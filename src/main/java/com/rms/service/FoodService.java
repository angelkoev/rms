package com.rms.service;

import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.FoodTypeEnum;
import com.rms.model.entity.OrderEntity;
import com.rms.repositiry.FoodRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final OrderService orderService;

    public FoodService(FoodRepository foodRepository, OrderService orderService) {
        this.foodRepository = foodRepository;
        this.orderService = orderService;
    }

    public void initFoods() {

        if (foodRepository.count() != 0) {
            return;
        }

        Random random = new Random();

        OrderEntity order = orderService.findById(1L);

        initFood(random, order,"Шопса", FoodTypeEnum.SALAD);
        initFood(random, order,"Овчарска", FoodTypeEnum.SALAD);
        initFood(random, order,"Зеле с моркови", FoodTypeEnum.SALAD);
        initFood(random, order,"Гръцка", FoodTypeEnum.SALAD);
        initFood(random, order,"Мусака", FoodTypeEnum.MAIN_COURSE);
        initFood(random, order,"Боб яхния", FoodTypeEnum.MAIN_COURSE);
        initFood(random, order,"Пиле с картофи", FoodTypeEnum.MAIN_COURSE);
        initFood(random, order,"Свинско със злее", FoodTypeEnum.MAIN_COURSE);
        initFood(random, order,"Свинска кавърма", FoodTypeEnum.MAIN_COURSE);
        initFood(random, order,"Сандвич 1", FoodTypeEnum.SANDWICH);
        initFood(random, order,"Сандвич 2", FoodTypeEnum.SANDWICH);
        initFood(random, order,"Сандвич 3", FoodTypeEnum.SANDWICH);
        initFood(random, order,"Сицилиана", FoodTypeEnum.PIZZA);
        initFood(random, order,"Вегетариана", FoodTypeEnum.PIZZA);
        initFood(random, order,"Неаполитанска", FoodTypeEnum.PIZZA);
        initFood(random, order,"Пепепрони", FoodTypeEnum.PIZZA);
        initFood(random, order,"Маргарита", FoodTypeEnum.PIZZA);
        initFood(random, order,"Ребра", FoodTypeEnum.BBQ);
        initFood(random, order,"Вратна пържола", FoodTypeEnum.BBQ);
        initFood(random, order,"Свински сач", FoodTypeEnum.BBQ);
        initFood(random, order,"кюфте", FoodTypeEnum.BBQ);
        initFood(random, order,"кебапче", FoodTypeEnum.BBQ);
        initFood(random, order,"наденичка", FoodTypeEnum.BBQ);
        initFood(random, order,"пилешко шишче", FoodTypeEnum.BBQ);
        initFood(random, order,"Пилешка", FoodTypeEnum.SOUP);
        initFood(random, order,"Топчета", FoodTypeEnum.SOUP);
        initFood(random, order,"Картофена", FoodTypeEnum.SOUP);
        initFood(random, order,"Крем супа", FoodTypeEnum.SOUP);
        initFood(random, order,"крем карамел", FoodTypeEnum.DESSERT);
        initFood(random, order,"суфле", FoodTypeEnum.DESSERT);
        initFood(random, order,"сладолед", FoodTypeEnum.DESSERT);
        initFood(random, order,"торта", FoodTypeEnum.DESSERT);
        initFood(random, order,"питка", FoodTypeEnum.BREAD);
        initFood(random, order,"бял хляб", FoodTypeEnum.BREAD);
        initFood(random, order,"пърленка", FoodTypeEnum.BREAD);
        initFood(random, order,"леща", FoodTypeEnum.VEGETARIAN);
        initFood(random, order,"спанак", FoodTypeEnum.VEGETARIAN);
        initFood(random, order,"картофи", FoodTypeEnum.VEGETARIAN);
        initFood(random, order,"ориз", FoodTypeEnum.VEGETARIAN);

    }

    private void initFood(Random random, OrderEntity order, String foodName, FoodTypeEnum foodTypeEnum) {
        double randomValuePrice = 10 + (50 - 10) * random.nextDouble(); // random between 10 and 50
        double randomValueCalories = 100 + (500 - 100) * random.nextDouble(); // random between 10 and 50

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setOrder(order);
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
}
