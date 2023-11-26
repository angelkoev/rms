package com.rms.service;

import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.OrderEntity;
import com.rms.repositiry.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
//    private final TableService tableService;
    private final FoodService foodService;
    private final DrinkService drinkService;

    public OrderService(OrderRepository orderRepository, FoodService foodService, DrinkService drinkService) {
        this.orderRepository = orderRepository;
//        this.tableService = tableService;
        this.foodService = foodService;
        this.drinkService = drinkService;
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
            orderRepository.save(menu);
//        }
    }

    public void initMenu() {

        Set<FoodEntity> allFoodsInMenu = foodService.findAllByOrderId(1);
        Set<DrinkEntity> allDrinksInMenu = drinkService.findAllByOrderId(1);

        Optional<OrderEntity> menuOpt = orderRepository.findById(1L);

        if (menuOpt.isPresent()) {
            OrderEntity menu = menuOpt.get();
            menu.getDrinks().addAll(allDrinksInMenu);
            menu.getFoods().addAll(allFoodsInMenu);
            orderRepository.save(menu);
        }

    }

    public OrderEntity findById (Long id) {
        return orderRepository.findOrderEntitiesById(id).orElse(null);
    }
}
