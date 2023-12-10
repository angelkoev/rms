package com.rms.service;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import org.springframework.cache.annotation.Cacheable;

import java.beans.Transient;
import java.util.List;

public interface OrderService {
    OrderEntity findById(Long id);

    //    @Cacheable("menuCache")
    OrderEntity getMenu();

    @Cacheable("drinksCache")
    List<DrinkView> getAllDrinksView();

    @Cacheable("foodsCache")
    List<FoodView> getAllFoodsView();

    @Transient
    void saveOrder(OrderEntity orderEntity);

    boolean isMenuOK();

    void addToMenu(DrinkEntity drinkEntity);

    void addToMenu(FoodEntity foodEntity);

    //    @CacheEvict(value = "drinksCache", allEntries = true) // MOVED LOGIC TO EVENT
    void clearDrinksCache();

    //    @CacheEvict(value = "foodsCache", allEntries = true) // MOVED LOGIC TO EVENT
    void clearFoodsCache();

    void triggerDrinksCacheEviction();

    void triggerFoodsCacheEviction();

    void addDrink(DrinkDTO drinkDTO, boolean addToMenu);

    void addFood(FoodDTO foodDTO, boolean addToMenu);

    OrderEntity makeNewOrder(UserEntity currentUser);

    List<OrderEntity> allOrdersByUsername(String username);

    List<OrderEntity> getAllOrders();
}
