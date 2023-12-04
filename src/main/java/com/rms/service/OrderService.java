package com.rms.service;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.repositiry.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    //    private final TableService tableService;
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, FoodService foodService, DrinkService drinkService, UserService userService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
//        this.tableService = tableService;
        this.foodService = foodService;
        this.drinkService = drinkService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void initMenuOrder() {

        if (orderRepository.count() != 0) {
            return;
        }

        OrderEntity menu = new OrderEntity();

//        TableEntity table = tableService.findById(1L); //taking 1 table

//        if (table != null) {
        menu.setCompleted(true);
//            menu.setTable(table);
        menu.setPaid(true);
//        menu.setMadeBy(userService.findById(1L).get());
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

    @Transient
    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.save(orderEntity);
    }

    public OrderEntity createNewLastOrder(UserEntity userEntity) {
        OrderEntity lastOrder = new OrderEntity();
        lastOrder.setDateTime(LocalDateTime.now());
        lastOrder.setMadeBy(userEntity);
//        orderRepository.save(lastOrder);

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

//        if (drinkService.isDrinkAlreadyAdded(drinkToAdd)) {
//            throw new Exception("Вече има напитка със същото име!");
//        }
        drinkService.addDrink(drinkToAdd);

        if (addToMenu) {
            addToMenu(drinkToAdd);
        }
    }

    public void addFood(FoodDTO foodDTO, boolean addToMenu) {

        FoodEntity foodToAdd = modelMapper.map(foodDTO, FoodEntity.class);

//        if (drinkService.isDrinkAlreadyAdded(foodToAdd)) {
//            throw new Exception("Вече има храна със същото име!");
//        }
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
}
