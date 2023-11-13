package com.rms.init;

//import com.rms.service.DrinkTypeServiceImpl;
//import com.rms.service.FoodTypeServiceImpl;

import com.rms.service.UserRoleServiceImpl;
import com.rms.service.UserService;
//import com.rms.service.interfaces.DrinkTypeService;
//import com.rms.service.interfaces.FoodTypeService;
//import com.rms.service.interfaces.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Order(0)
@Component
public class DatabaseInit implements CommandLineRunner {

    private final UserRoleServiceImpl userRoleService;
    private final UserService userService;
//    private final DrinkTypeServiceImpl drinkTypeService;
//    private final FoodTypeServiceImpl foodTypeService;

    public DatabaseInit(UserRoleServiceImpl userRoleService, UserService userService
//            , DrinkTypeServiceImpl drinkTypeService, FoodTypeServiceImpl foodTypeService
    ) {
        this.userRoleService = userRoleService;
        this.userService = userService;
//        this.drinkTypeService = drinkTypeService;
//        this.foodTypeService = foodTypeService;
    }

    @Override
    public void run(String... args) throws Exception {


        userRoleService.initUserRoles();
//        drinkTypeService.initDrinkTypes();
//        foodTypeService.initFoodTypes();

        userService.addAdmin();
        userService.addEmployees();
        userService.AddClients();

        // TODO -> initTables - > 10 tables with 2 waiters
        // TODO -> init comments -> 3-5 comments from "users"
        // TODO -> init menu -> put initial food and drinks in the menu !!!


        // TODO -> add some test data for users and menu !!!

    }
}

