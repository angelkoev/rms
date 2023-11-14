package com.rms.init;

//import com.rms.service.DrinkTypeServiceImpl;
//import com.rms.service.FoodTypeServiceImpl;

import com.rms.service.*;
//import com.rms.service.interfaces.DrinkTypeService;
//import com.rms.service.interfaces.FoodTypeService;
//import com.rms.service.interfaces.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Order(0)
@Component
public class DatabaseInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    private final TableService tableService;
    private final ReviewService reviewService;
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final OrderService orderService;
//    private final DrinkTypeServiceImpl drinkTypeService;
//    private final FoodTypeServiceImpl foodTypeService;

    public DatabaseInit(UserRoleService userRoleService, UserService userService,
//            , DrinkTypeServiceImpl drinkTypeService, FoodTypeServiceImpl foodTypeService
                        TableService tableService, ReviewService reviewService, FoodService foodService, DrinkService drinkService, OrderService orderService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
//        this.drinkTypeService = drinkTypeService;
//        this.foodTypeService = foodTypeService;
        this.tableService = tableService;
        this.reviewService = reviewService;

        this.orderService = orderService;

        this.foodService = foodService;
        this.drinkService = drinkService;
    }

    @Override
    public void run(String... args) throws Exception {


        userRoleService.initUserRoles();
//        drinkTypeService.initDrinkTypes();
//        foodTypeService.initFoodTypes();

        userService.initAdmin();
        userService.initUsers();
//        userService.initClients();

        tableService.initTables();
        reviewService.initReviews();

        orderService.initMenuOrder();

        foodService.initFoods();
        drinkService.initDrinks();

        orderService.initMenu();


        // TODO -> init menu -> put initial food and drinks in the menu !!!


        // TODO -> add some test data for users and menu !!!

    }
}

