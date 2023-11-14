package com.rms.service;

import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.FoodTypeEnum;
import com.rms.model.entity.OrderEntity;
import com.rms.repositiry.FoodRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public void initFoods() {

        if (foodRepository.count() != 0) {
            return;
        }

        Random random = new Random();

//        OrderEntity order = orderService.findById(1L);

        initFood(random, "Шопса", FoodTypeEnum.SALAD);
        initFood(random, "Овчарска", FoodTypeEnum.SALAD);
        initFood(random, "Зеле с моркови", FoodTypeEnum.SALAD);
        initFood(random, "Гръцка", FoodTypeEnum.SALAD);
        initFood(random, "Мусака", FoodTypeEnum.MAIN_COURSE);
        initFood(random, "Боб яхния", FoodTypeEnum.MAIN_COURSE);
        initFood(random, "Пиле с картофи", FoodTypeEnum.MAIN_COURSE);
        initFood(random, "Свинско със злее", FoodTypeEnum.MAIN_COURSE);
        initFood(random, "Свинска кавърма", FoodTypeEnum.MAIN_COURSE);
        initFood(random, "Сандвич 1", FoodTypeEnum.SANDWICH);
        initFood(random, "Сандвич 2", FoodTypeEnum.SANDWICH);
        initFood(random, "Сандвич 3", FoodTypeEnum.SANDWICH);
        initFood(random, "Сицилиана", FoodTypeEnum.PIZZA);
        initFood(random, "Вегетариана", FoodTypeEnum.PIZZA);
        initFood(random, "Неаполитанска", FoodTypeEnum.PIZZA);
        initFood(random, "Пепепрони", FoodTypeEnum.PIZZA);
        initFood(random, "Маргарита", FoodTypeEnum.PIZZA);
        initFood(random, "Ребра", FoodTypeEnum.BBQ);
        initFood(random, "Вратна пържола", FoodTypeEnum.BBQ);
        initFood(random, "Свински сач", FoodTypeEnum.BBQ);
        initFood(random, "кюфте", FoodTypeEnum.BBQ);
        initFood(random, "кебапче", FoodTypeEnum.BBQ);
        initFood(random, "наденичка", FoodTypeEnum.BBQ);
        initFood(random, "пилешко шишче", FoodTypeEnum.BBQ);
        initFood(random, "Пилешка", FoodTypeEnum.SOUP);
        initFood(random, "Топчета", FoodTypeEnum.SOUP);
        initFood(random, "Картофена", FoodTypeEnum.SOUP);
        initFood(random, "Крем супа", FoodTypeEnum.SOUP);
        initFood(random, "крем карамел", FoodTypeEnum.DESSERT);
        initFood(random, "суфле", FoodTypeEnum.DESSERT);
        initFood(random, "сладолед", FoodTypeEnum.DESSERT);
        initFood(random, "торта", FoodTypeEnum.DESSERT);
        initFood(random, "питка", FoodTypeEnum.BREAD);
        initFood(random, "бял хляб", FoodTypeEnum.BREAD);
        initFood(random, "пърленка", FoodTypeEnum.BREAD);
        initFood(random, "леща", FoodTypeEnum.VEGETARIAN);
        initFood(random, "спанак", FoodTypeEnum.VEGETARIAN);
        initFood(random, "картофи", FoodTypeEnum.VEGETARIAN);
        initFood(random, "ориз", FoodTypeEnum.VEGETARIAN);

    }

    private void initFood(Random random, String foodName, FoodTypeEnum foodTypeEnum) {
        double randomValuePrice = 10 + (50 - 10) * random.nextDouble(); // random between 10 and 50
        double randomValueCalories = 100 + (500 - 100) * random.nextDouble(); // random between 10 and 50

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setOrderId(1);
        foodEntity.setName(foodName);
        foodEntity.setPrice(BigDecimal.valueOf(randomValuePrice));
        foodEntity.setType(foodTypeEnum);

        foodEntity.setSize(random.nextInt(301) + 200); // random int from 300 to 500
        foodEntity.setKcal(BigDecimal.valueOf(randomValueCalories));
        foodEntity.setPreparationTime(random.nextInt(20) + 10);
        foodEntity.setCompleted(true);
        foodEntity.setDelivered(true);
        foodEntity.setPaid(true);

        foodRepository.save(foodEntity);

    }

    public Set<FoodEntity> findAllByOrderId (int id) {
        return foodRepository.findAllByOrderId(id);
    }
}
