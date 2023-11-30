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
        initDrink(random, "Кафе", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Капучино", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Лате", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Фрапе", DrinkTypeEnum.COFFEE_BASED);
        initDrink(random, "Червено вино", DrinkTypeEnum.WINE);
        initDrink(random, "Бяло вино", DrinkTypeEnum.WINE);
        initDrink(random, "Розе", DrinkTypeEnum.WINE);
        initDrink(random, "Шампанско", DrinkTypeEnum.WINE);
        initDrink(random, "Плодов чай", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Горещ шоколад", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Зелен чай", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Черен чай", DrinkTypeEnum.HOT_BEVERAGE);
        initDrink(random, "Ябълков сок", DrinkTypeEnum.JUICE);
        initDrink(random, "Портокалов сок", DrinkTypeEnum.JUICE);
        initDrink(random, "Сок от ананас", DrinkTypeEnum.JUICE);
        initDrink(random, "Сок от моркови", DrinkTypeEnum.JUICE);
        initDrink(random, "Лагер", DrinkTypeEnum.BEER);
        initDrink(random, "Ейл", DrinkTypeEnum.BEER);
        initDrink(random, "Пилснер", DrinkTypeEnum.BEER);
        initDrink(random, "Тъмна", DrinkTypeEnum.BEER);
        initDrink(random, "Кока кола", DrinkTypeEnum.SODA);
        initDrink(random, "Фанта", DrinkTypeEnum.SODA);
        initDrink(random, "Спрайт", DrinkTypeEnum.SODA);
        initDrink(random, "Тоник", DrinkTypeEnum.SODA);
        initDrink(random, "Маргарита", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Дайкири", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Манхатън", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Секс на плажа", DrinkTypeEnum.COCKTAIL);
        initDrink(random, "Ром", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Водка", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Уиски", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Джин", DrinkTypeEnum.ALCOHOLIC);
        initDrink(random, "Текила", DrinkTypeEnum.ALCOHOLIC);

    }

    private void initDrink(Random random, String drinkName, DrinkTypeEnum drinkType) {
        double randomValuePrice = 10 + (50 - 10) * random.nextDouble(); // random between 10 and 50

        DrinkEntity drinkEntity = new DrinkEntity();
        drinkEntity.setName(drinkName);
        drinkEntity.setPrice(BigDecimal.valueOf(randomValuePrice));
        drinkEntity.setType(drinkType);

        drinkEntity.setVolume(random.nextInt(301) + 200); // random int from 300 to 500

        drinkRepository.save(drinkEntity);
    }

    public Set<DrinkEntity> findAllBy() {
        return drinkRepository.findAllBy();
    }

    public Set<DrinkEntity> findAllByTypeAndOrderId(DrinkTypeEnum drinkTypeEnum) {
        return drinkRepository.findAllByTypeEquals(drinkTypeEnum);
    }

    public DrinkEntity findById(Long id) {
        return drinkRepository.findById(id).orElse(null);
    }
}
