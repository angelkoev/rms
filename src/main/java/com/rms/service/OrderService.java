package com.rms.service;

import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.model.entity.UserEntity;
import com.rms.repositiry.OrderRepository;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    //    private final TableService tableService;
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, FoodService foodService, DrinkService drinkService, UserService userService) {
        this.orderRepository = orderRepository;
//        this.tableService = tableService;
        this.foodService = foodService;
        this.drinkService = drinkService;
        this.userService = userService;
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
        menu.setDateTime(LocalDateTime.now());
        Set<FoodEntity> allFoodsInMenu = foodService.findAllBy();
        Set<DrinkEntity> allDrinksInMenu = drinkService.findAllBy();
        menu.getDrinks().addAll(allDrinksInMenu);
        menu.getFoods().addAll(allFoodsInMenu);

        orderRepository.save(menu);
//        }
    }

//    public void initMenu() {
//
//        Set<FoodEntity> allFoodsInMenu = foodService.findAllBy();
//        Set<DrinkEntity> allDrinksInMenu = drinkService.findAllBy();
//
//        Optional<OrderEntity> menuOpt = orderRepository.findById(1L);
//
//        if (menuOpt.isPresent()) {
//            OrderEntity menu = menuOpt.get();
//            menu.setMadeBy(userService.findById(1L).get());
//            menu.getDrinks().addAll(allDrinksInMenu);
//            menu.getFoods().addAll(allFoodsInMenu);
//            orderRepository.save(menu);
//        }
//
//    }

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

}
