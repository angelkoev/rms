package com.rms.service.Impl;

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
import com.rms.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final FoodServiceImpl foodServiceImpl;
    private final DrinkServiceImpl drinkServiceImpl;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;
//    private static OrderEntity menu = null;

    public OrderServiceImpl(OrderRepository orderRepository, FoodServiceImpl foodServiceImpl, DrinkServiceImpl drinkServiceImpl, ModelMapper modelMapper, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.foodServiceImpl = foodServiceImpl;
        this.drinkServiceImpl = drinkServiceImpl;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
    }
    @Override
    public OrderEntity findById(Long id) {
        return orderRepository.findOrderEntitiesById(id).orElse(null);
    }
    @Override

//    @Cacheable("menuCache")
    public OrderEntity getMenu() {
        System.out.println("CACHE CHECK MENU");
        OrderEntity menu = orderRepository.findById(1L).orElse(null);
        return menu;
    }

    @Override
    @Cacheable("drinksCache")
    public List<DrinkView> getAllDrinksView() {
        System.out.println("CACHE CHECK DRINKS");
        List<DrinkEntity> allDrinks = getMenu().getDrinks();
        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();

        return allDrinksView;
    }
    @Override
    @Cacheable("foodsCache")
    public List<FoodView> getAllFoodsView() {
        System.out.println("CACHE CHECK FOODS");
        List<FoodEntity> allFoods = getMenu().getFoods();
        List<FoodView> allFoodsView = allFoods.stream().map(foodEntity -> modelMapper.map(foodEntity, FoodView.class)).toList();

        return allFoodsView;
    }
    @Override
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
    @Override
    public boolean isMenuOK() {
        return getMenu() != null;
    }
    @Override
    public void addToMenu(DrinkEntity drinkEntity) {
        OrderEntity menu = getMenu();
        menu.getDrinks().add(drinkEntity);
        orderRepository.save(menu);
//        menu = getMenu();
    }
    @Override
    public void addToMenu(FoodEntity foodEntity) {
        OrderEntity menu = getMenu();
        menu.getFoods().add(foodEntity);
        orderRepository.save(menu);
//        menu = getMenu();
    }
    @Override
//    @CacheEvict(value = "drinksCache", allEntries = true) // MOVED LOGIC TO EVENT
    public void clearDrinksCache() {
        System.out.println("CLEAR CACHE DRINKS");
        triggerDrinksCacheEviction();
        // to clear drinks cache when new drink is added
    }
    @Override
//    @CacheEvict(value = "foodsCache", allEntries = true) // MOVED LOGIC TO EVENT
    public void clearFoodsCache() {
        System.out.println("CLEAR CACHE FOODS");
        triggerFoodsCacheEviction();
        // to clear foods cache when new drink is added
    }
    @Override
    public void triggerDrinksCacheEviction() {
        eventPublisher.publishEvent(new DrinksCacheEvictEvent(this));
    }
    @Override
    public void triggerFoodsCacheEviction() {
        eventPublisher.publishEvent(new FoodsCacheEvictEvent(this));
    }
    @Override
    public void addDrink(DrinkDTO drinkDTO, boolean addToMenu) {

        DrinkEntity drinkToAdd = modelMapper.map(drinkDTO, DrinkEntity.class);

        drinkServiceImpl.addDrink(drinkToAdd);

        if (addToMenu) {
            addToMenu(drinkToAdd);
        }
    }
    @Override
    public void addFood(FoodDTO foodDTO, boolean addToMenu) {

        FoodEntity foodToAdd = modelMapper.map(foodDTO, FoodEntity.class);

        foodServiceImpl.addFood(foodToAdd);

        if (addToMenu) {
            addToMenu(foodToAdd);
        }
    }
    @Override
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
    @Override
    public List<OrderEntity> allOrdersByUsername(String username) {
        return orderRepository.findOrderEntitiesByMadeBy_UsernameOrderByDateTimeDesc(username);
    }
    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.getAllByOrderByDateTimeDesc();
    }

}
