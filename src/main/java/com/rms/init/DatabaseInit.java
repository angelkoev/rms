package com.rms.init;

import com.rms.service.interfaces.DrinkTypeService;
import com.rms.service.interfaces.FoodTypeService;
import com.rms.service.interfaces.UserRoleService;
import com.rms.service.interfaces.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {

    private final UserRoleService userRoleService;

    private final UserService userService;
    private final DrinkTypeService drinkTypeService;
    private final FoodTypeService foodTypeService;

    public DatabaseInit(UserRoleService userRoleService, UserService userService, DrinkTypeService drinkTypeService, FoodTypeService foodTypeService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.drinkTypeService = drinkTypeService;
        this.foodTypeService = foodTypeService;
    }


    @Override
    public void run(String... args) throws Exception {


        userRoleService.initUserRoles();
        drinkTypeService.initDrinkTypes();
        foodTypeService.initFoodTypes();

//        userService.addAdmin();
//        userService.addEmployees();

        // TODO -> add some test data for users and menu !!!

    }
}

