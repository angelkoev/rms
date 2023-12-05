package com.rms.service;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.repositiry.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final FoodService foodService;
    private final DrinkService drinkService;
    private final ModelMapper modelMapper;

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

    public OrderEntity getMenu() {
        return orderRepository.findById(1L).orElse(null);
    }

    public List<DrinkView> getAllDrinksView(String username) {

        List<DrinkEntity> allDrinks = getMenu().getDrinks();
        List<DrinkView> allDrinksView = allDrinks.stream().map(drinkEntity -> modelMapper.map(drinkEntity, DrinkView.class)).toList();

        return allDrinksView;
    }
    public List<FoodView> getAllFoodsView(String username) {

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

    public void addToMenu(DrinkEntity drinkEntity) {
        OrderEntity menu = getMenu();
        menu.getDrinks().add(drinkEntity);
        orderRepository.save(menu);
    }

    public void addToMenu(FoodEntity foodEntity) {
        OrderEntity menu = getMenu();
        menu.getFoods().add(foodEntity);
        orderRepository.save(menu);
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
