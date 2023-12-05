package com.rms.service;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.repositiry.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final ModelMapper modelMapper;
    private static OrderEntity menu = null;

    public OrderService(OrderRepository orderRepository, FoodService foodService, DrinkService drinkService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.foodService = foodService;
        this.drinkService = drinkService;
        this.modelMapper = modelMapper;
    }

    public void initMenuOrder() {

        if (orderRepository.count() != 0) {
            return;
        }

        OrderEntity menu = new OrderEntity();

        menu.setCompleted(true);
        menu.setPaid(true);
        menu.setDateTime(LocalDateTime.now());
        Set<FoodEntity> allFoodsInMenu = foodService.findAllBy();
        Set<DrinkEntity> allDrinksInMenu = drinkService.findAllBy();
        menu.getDrinks().addAll(allDrinksInMenu);
        menu.getFoods().addAll(allFoodsInMenu);

        orderRepository.save(menu);
//        }
    }

    public OrderEntity findById(Long id) {
        return orderRepository.findOrderEntitiesById(id).orElse(null);
    }

//    @Cacheable("menuCache")
    public OrderEntity getMenu() {
        System.out.println("CACHE CHECK MENU");
        menu = orderRepository.findById(1L).orElse(null);
        return menu;
    }

    @Cacheable("drinksCache")
    public List<DrinkView> getAllDrinksView() {
        System.out.println("CACHE CHECK DRINKS");
        List<DrinkEntity> allDrinks = menu.getDrinks();
        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();

        return allDrinksView;
    }

    @Cacheable("foodsCache")
    public List<FoodView> getAllFoodsView() {
        System.out.println("CACHE CHECK FOODS");
        List<FoodEntity> allFoods = menu.getFoods();
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
        return menu != null;
    }

    public void addToMenu(DrinkEntity drinkEntity) {
        menu.getDrinks().add(drinkEntity);
        orderRepository.save(menu);
    }

    public void addToMenu(FoodEntity foodEntity) {
        menu.getFoods().add(foodEntity);
        orderRepository.save(menu);
    }

    @CacheEvict(value = "drinksCache", allEntries = true)
    public void clearDrinksCache() {
        System.out.println("CLEAR CACHE DRINKS");
        // to clear drinks cache when new drink is added
    }
    @CacheEvict(value = "foodsCache", allEntries = true)
    public void clearFoodsCache() {
        System.out.println("CLEAR CACHE FOODS");
        // to clear foods cache when new drink is added
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
