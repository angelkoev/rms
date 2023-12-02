package com.rms.service;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.*;
import com.rms.repositiry.FoodRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
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

        initFood(random, "Шопса салата", FoodTypeEnum.SALAD);
        initFood(random, "Овчарска салата", FoodTypeEnum.SALAD);
        initFood(random, "Салата зеле с моркови", FoodTypeEnum.SALAD);
        initFood(random, "Гръцка салата", FoodTypeEnum.SALAD);
        initFood(random, "Мусака", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Боб яхния", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Пиле с картофи", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Свинско със зеле", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Свинска кавърма", FoodTypeEnum.MAIN_DISH);
        initFood(random, "Сандвич с шунка", FoodTypeEnum.SANDWICH);
        initFood(random, "Сандвич с кашкавал", FoodTypeEnum.SANDWICH);
        initFood(random, "Веган сандвич", FoodTypeEnum.SANDWICH);
        initFood(random, "Пица Сицилиана", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Вегетариана", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Неаполитана", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Пепепрони", FoodTypeEnum.PIZZA);
        initFood(random, "Пица Маргарита", FoodTypeEnum.PIZZA);
        initFood(random, "Ребърца", FoodTypeEnum.BBQ);
        initFood(random, "Вратна пържола", FoodTypeEnum.BBQ);
        initFood(random, "Свински сач", FoodTypeEnum.BBQ);
        initFood(random, "Кюфте", FoodTypeEnum.BBQ);
        initFood(random, "Кебапче", FoodTypeEnum.BBQ);
        initFood(random, "Наденичка", FoodTypeEnum.BBQ);
        initFood(random, "Пилешко шишче", FoodTypeEnum.BBQ);
        initFood(random, "Пилешка супа", FoodTypeEnum.SOUP);
        initFood(random, "Супа топчета", FoodTypeEnum.SOUP);
        initFood(random, "Картофена супа", FoodTypeEnum.SOUP);
        initFood(random, "Крем супа", FoodTypeEnum.SOUP);
        initFood(random, "Крем карамел", FoodTypeEnum.DESSERT);
        initFood(random, "Суфле", FoodTypeEnum.DESSERT);
        initFood(random, "Сладолед", FoodTypeEnum.DESSERT);
        initFood(random, "Торта", FoodTypeEnum.DESSERT);
        initFood(random, "Питка", FoodTypeEnum.BREAD);
        initFood(random, "Бял хляб", FoodTypeEnum.BREAD);
        initFood(random, "Пърленка", FoodTypeEnum.BREAD);
        initFood(random, "Леща", FoodTypeEnum.VEGETARIAN);
        initFood(random, "Спанак", FoodTypeEnum.VEGETARIAN);
        initFood(random, "Картофи", FoodTypeEnum.VEGETARIAN);
        initFood(random, "Ориз", FoodTypeEnum.VEGETARIAN);

    }

    private void initFood(Random random, String foodName, FoodTypeEnum foodTypeEnum) {
        double randomValuePrice = 10 + (50 - 10) * random.nextDouble(); // random between 10 and 50
        double randomValueCalories = 100 + (500 - 100) * random.nextDouble(); // random between 10 and 50

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setName(foodName);
        foodEntity.setPrice(BigDecimal.valueOf(randomValuePrice));
        foodEntity.setType(foodTypeEnum);

        foodEntity.setSize(random.nextInt(301) + 200); // random int from 300 to 500
        foodEntity.setKcal(BigDecimal.valueOf(randomValueCalories));

        foodRepository.save(foodEntity);

    }

    public boolean isFoodAlreadyAdded(FoodEntity foodEntity) {
        Optional<DrinkEntity> findByName = foodRepository.findByName(foodEntity.getName());

        return findByName.isPresent();
    }

    public boolean isFoodAlreadyAdded(FoodDTO foodDTO) {
        Optional<DrinkEntity> findByName = foodRepository.findByName(foodDTO.getName());

        return findByName.isPresent();
    }

    public void addDrink(FoodEntity foodEntity) {

        if (isFoodAlreadyAdded(foodEntity)) {
            throw new IllegalStateException(); // FIXME fix the error name
        }

        foodRepository.save(foodEntity);
    }

    public Set<FoodEntity> findAllBy () {
        return foodRepository.findAllBy();
    }

    public Set<FoodEntity> findAllByType(FoodTypeEnum foodTypeEnum) {
        return foodRepository.findAllByTypeEquals(foodTypeEnum);
    }

    public FoodEntity findById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }
}
