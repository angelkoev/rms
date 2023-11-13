package com.rms.service;

import com.rms.model.entity.*;
import com.rms.repositiry.DrinkRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final OrderService orderService;

    public DrinkService(DrinkRepository drinkRepository, OrderService orderService) {
        this.drinkRepository = drinkRepository;
        this.orderService = orderService;
    }

    public void initDrinks() {
        
        if (drinkRepository.count() != 0) {
            return;
        }
        
        Random random = new Random();

        OrderEntity order = orderService.findById(1L);

//  ,   ALCOHOLIC
        initDrink(random, order, "Coffee", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, order, "Cappuccino", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, order, "Latte", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, order, "Frappe", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, order, "Red Wine", DrinkTypeEnum.WINE);
        initDrink(random, order, "White Wine", DrinkTypeEnum.WINE);
        initDrink(random, order, "Rose", DrinkTypeEnum.WINE);
        initDrink(random, order, "Champagne", DrinkTypeEnum.WINE);
        initDrink(random, order, "Tea", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, order, "Hot Chocolate", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, order, "Green Tea", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, order, "Black Tea", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, order, "Apple Juice", DrinkTypeEnum.JUICE);
        initDrink(random, order, "Orange Juice", DrinkTypeEnum.JUICE);
        initDrink(random, order, "Grape Juice", DrinkTypeEnum.JUICE);
        initDrink(random, order, "Carrot Juice", DrinkTypeEnum.JUICE);
        initDrink(random, order, "Lager", DrinkTypeEnum.BEER);
        initDrink(random, order, "Ale", DrinkTypeEnum.BEER);
        initDrink(random, order, "Pilsner", DrinkTypeEnum.BEER);
        initDrink(random, order, "Bock", DrinkTypeEnum.BEER);
        initDrink(random, order, "Coca cola", DrinkTypeEnum.SODA);
        initDrink(random, order, "Fanta", DrinkTypeEnum.SODA);
        initDrink(random, order, "Sprite", DrinkTypeEnum.SODA);
        initDrink(random, order, "Tonic", DrinkTypeEnum.SODA);
        initDrink(random, order, "Margarita", DrinkTypeEnum.COCKTAIL);
        initDrink(random, order, "Daiquiri", DrinkTypeEnum.COCKTAIL);
        initDrink(random, order, "Manhattan", DrinkTypeEnum.COCKTAIL);
        initDrink(random, order, "Sex on the Beach", DrinkTypeEnum.COCKTAIL);
        initDrink(random, order, "Rum", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, order, "Vodka", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, order, "Whisky", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, order, "Jin", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, order, "Tequila", DrinkTypeEnum.ALCOHOLIC);

    }

    private void initDrink(Random random, OrderEntity order, String drinkName, DrinkTypeEnum drinkType) {
        double randomValuePrice = 10 + (50 - 10) * random.nextDouble(); // random between 10 and 50

        DrinkEntity drinkEntity = new DrinkEntity();
        drinkEntity.setOrder(order);
        drinkEntity.setName(drinkName);
        drinkEntity.setPrice(BigDecimal.valueOf(randomValuePrice));
        drinkEntity.setType(drinkType);

        drinkEntity.setVolume(random.nextInt(301) + 200); // random int from 300 to 500
        drinkEntity.setPreparationTime(random.nextInt(20) + 10);
        drinkEntity.setCompleted(true);
        drinkEntity.setDelivered(true);
        drinkEntity.setPaid(true);

        drinkRepository.save(drinkEntity);
    }
}
