package com.rms.service;

import com.rms.model.entity.*;
import com.rms.repositiry.FoodTypeRepository;
//import com.rms.service.interfaces.FoodTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FoodTypeServiceImpl
//        implements FoodTypeService
{

    private final FoodTypeRepository foodTypeRepository;

    public FoodTypeServiceImpl(FoodTypeRepository foodTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
    }

//    @Override
    public void initFoodTypes() {
        if (foodTypeRepository.count() != 0) {
            return;
        }

        Arrays.stream(FoodTypeEnum.values())
                .forEach(foodTypeEnum -> {
                    FoodType foodType = new FoodType();
                    foodType.setName(foodTypeEnum);

                    switch (foodTypeEnum) {
                        case SALAD -> foodType.setDescription("ceaser, fruit, greek, shopska, shepard, etc");
                        case SOUP -> foodType.setDescription("tomato, onion, mushroom, chicken, etc");
                        case DESSERT -> foodType.setDescription("cheesecake, cake, cupcake, pudding, tiramisu, creme brulee, creme caramel");
                        case MAIN_COURSE -> foodType.setDescription("musaka, filled peppers, pork with rice, pork with potatoes, etc");
                        case SANDWICH -> foodType.setDescription("grilled cheese, ham and cheese, grilled chicken, peanut butter and jelly");
                        case PIZZA -> foodType.setDescription("margherita, peperoni, neapolitana, vegetariana");
                        case BBQ -> foodType.setDescription("meatball, sausage, steak, ribs, pleskavitza");
                        case VEGETARIAN -> foodType.setDescription("rice, potatoes, cabbage, oat, wheat");
                        case BREAD -> foodType.setDescription("white, brown, banitza, purlenka");
                    }

                    foodTypeRepository.save(foodType);
                });
    }
}
