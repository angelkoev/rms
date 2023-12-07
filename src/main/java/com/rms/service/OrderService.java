package com.rms.service;

import com.rms.events.DrinksCacheEvictEvent;
import com.rms.events.FoodsCacheEvictEvent;
import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;
//    private static OrderEntity menu = null;

    public OrderService(OrderRepository orderRepository, FoodService foodService, DrinkService drinkService, ModelMapper modelMapper, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.foodService = foodService;
        this.drinkService = drinkService;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
    }
    public OrderEntity findById(Long id) {
        return orderRepository.findOrderEntitiesById(id).orElse(null);
    }

//    @Cacheable("menuCache")
    public OrderEntity getMenu() {
        System.out.println("CACHE CHECK MENU");
        OrderEntity menu = orderRepository.findById(1L).orElse(null);
        return menu;
    }

    @Cacheable("drinksCache")
    public List<DrinkView> getAllDrinksView() {
        System.out.println("CACHE CHECK DRINKS");
        List<DrinkEntity> allDrinks = getMenu().getDrinks();
        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();

        return allDrinksView;
    }

    @Cacheable("foodsCache")
    public List<FoodView> getAllFoodsView() {
        System.out.println("CACHE CHECK FOODS");
        List<FoodEntity> allFoods = getMenu().getFoods();
        List<FoodView> allFoodsView = allFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();

        return allFoodsView;
    }

    @Transient
    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    public OrderEntity createNewLastOrder(UserEntity userEntity) {
        OrderEntity lastOrder = new OrderEntity();
        lastOrder.setDateTime(LocalDateTime.now());
        lastOrder.setMadeBy(userEntity);

        return lastOrder;
    }

    public boolean isMenuOK() {
        return getMenu() != null;
    }

    public void addToMenu(DrinkEntity drinkEntity) {
        OrderEntity menu = getMenu();
        menu.getDrinks().add(drinkEntity);
        orderRepository.save(menu);
//        menu = getMenu();
    }

    public void addToMenu(FoodEntity foodEntity) {
        OrderEntity menu = getMenu();
        menu.getFoods().add(foodEntity);
        orderRepository.save(menu);
//        menu = getMenu();
    }

//    @CacheEvict(value = "drinksCache", allEntries = true) // MOVED LOGIC TO EVENT
    public void clearDrinksCache() {
        System.out.println("CLEAR CACHE DRINKS");
        triggerDrinksCacheEviction();
        // to clear drinks cache when new drink is added
    }

//    @CacheEvict(value = "foodsCache", allEntries = true) // MOVED LOGIC TO EVENT
    public void clearFoodsCache() {
        System.out.println("CLEAR CACHE FOODS");
        triggerFoodsCacheEviction();
        // to clear foods cache when new drink is added
    }

    public void triggerDrinksCacheEviction() {
        eventPublisher.publishEvent(new DrinksCacheEvictEvent(this));
    }

    public void triggerFoodsCacheEviction() {
        eventPublisher.publishEvent(new FoodsCacheEvictEvent(this));
    }

    public void addDrink(DrinkDTO drinkDTO, boolean addToMenu) {

        DrinkEntity drinkToAdd = modelMapper.map(drinkDTO, DrinkEntity.class);

        drinkService.addDrink(drinkToAdd);

        if (addToMenu) {
            addToMenu(drinkToAdd);
        }
    }

    public void addFood(FoodDTO foodDTO, boolean addToMenu) {

        FoodEntity foodToAdd = modelMapper.map(foodDTO, FoodEntity.class);

        foodService.addFood(foodToAdd);

        if (addToMenu) {
            addToMenu(foodToAdd);
        }
    }

    public OrderEntity makeNewOrder(UserEntity currentUser) {
        OrderEntity newOrder = new OrderEntity();
        newOrder.setDateTime(LocalDateTime.now());
        newOrder.setMadeBy(currentUser);
        newOrder.setPaid(false);
        newOrder.setCompleted(false);
        newOrder.setDrinks(currentUser.getLastOrder().getDrinks());
        newOrder.setFoods(currentUser.getLastOrder().getFoods());
        orderRepository.save(newOrder);
        return newOrder;
    }

    public List<OrderEntity> allOrdersByUsername(String username) {
        return orderRepository.findOrderEntitiesByMadeBy_UsernameOrderByDateTimeDesc(username);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.getAllByOrderByDateTimeDesc();
    }

}
