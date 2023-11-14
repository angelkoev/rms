package com.rms.service;

import com.rms.model.entity.*;
import com.rms.repositiry.DrinkRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public void initDrinks() {
        
        if (drinkRepository.count() != 0) {
            return;
        }
        
        Random random = new Random();

//        OrderEntity order = orderService.findById(1L);

//  ,   ALCOHOLIC
        initDrink(random, "Coffee", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Cappuccino", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Latte", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Frappe", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Red Wine", DrinkTypeEnum.WINE);
        initDrink(random, "White Wine", DrinkTypeEnum.WINE);
        initDrink(random, "Rose", DrinkTypeEnum.WINE);
        initDrink(random, "Champagne", DrinkTypeEnum.WINE);
        initDrink(random, "Tea", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Hot Chocolate", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Green Tea", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Black Tea", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Apple Juice", DrinkTypeEnum.JUICE);
        initDrink(random, "Orange Juice", DrinkTypeEnum.JUICE);
        initDrink(random, "Grape Juice", DrinkTypeEnum.JUICE);
        initDrink(random, "Carrot Juice", DrinkTypeEnum.JUICE);
        initDrink(random, "Lager", DrinkTypeEnum.BEER);
        initDrink(random, "Ale", DrinkTypeEnum.BEER);
        initDrink(random, "Pilsner", DrinkTypeEnum.BEER);
        initDrink(random, "Bock", DrinkTypeEnum.BEER);
        initDrink(random, "Coca cola", DrinkTypeEnum.SODA);
        initDrink(random, "Fanta", DrinkTypeEnum.SODA);
        initDrink(random, "Sprite", DrinkTypeEnum.SODA);
        initDrink(random, "Tonic", DrinkTypeEnum.SODA);
        initDrink(random, "Margarita", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Daiquiri", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Manhattan", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Sex on the Beach", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Rum", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Vodka", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Whisky", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Jin", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Tequila", DrinkTypeEnum.ALCOHOLIC);

    }

    private void initDrink(Random random, String drinkName, DrinkTypeEnum drinkType) {
        double randomValuePrice = 10 + (50 - 10) * random.nextDouble(); // random between 10 and 50

        DrinkEntity drinkEntity = new DrinkEntity();
        drinkEntity.setOrderId(1);
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

    public Set<DrinkEntity> findAllByOrderId (int id) {
        return drinkRepository.findAllByOrderId(id);
    }
}
