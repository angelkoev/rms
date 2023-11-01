package com.rms.init;

import com.rms.service.interfaces.DrinkTypeService;
import com.rms.service.interfaces.FoodTypeService;
import com.rms.service.interfaces.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final DrinkTypeService drinkTypeService;
    private final FoodTypeService foodTypeService;

    public DatabaseInit(UserRoleService userRoleService, DrinkTypeService drinkTypeService, FoodTypeService foodTypeService) {
        this.userRoleService = userRoleService;
        this.drinkTypeService = drinkTypeService;
        this.foodTypeService = foodTypeService;
    }


    @Override
    public void run(String... args) throws Exception {


        userRoleService.initUserRoles();
        drinkTypeService.initDrinkTypes();
        foodTypeService.initFoodTypes();


        // TODO -> add some test data for users and menu !!!

    }
}

