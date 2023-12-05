package com.rms.Init;

//import com.rms.service.DrinkTypeServiceImpl;
//import com.rms.service.FoodTypeServiceImpl;

import com.rms.service.*;
//import com.rms.service.interfaces.DrinkTypeService;
//import com.rms.service.interfaces.FoodTypeService;
//import com.rms.service.interfaces.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Order(0)
@Component
public class DatabaseInit implements CommandLineRunner {

    private final UserRoleService userRoleService;
    private final UserService userService;
    //    private final TableService tableService;
    private final ReviewService reviewService;
    private final FoodService foodService;
    private final DrinkService drinkService;
    private final OrderService orderService;
    //    private final DrinkTypeServiceImpl drinkTypeService;
//    private final FoodTypeServiceImpl foodTypeService;
    @Autowired
    private final TestDataInit testDataInit;

    public DatabaseInit(UserRoleService userRoleService, UserService userService,
                        ReviewService reviewService, FoodService foodService, DrinkService drinkService, OrderService orderService, TestDataInit testDataInit) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.orderService = orderService;
        this.foodService = foodService;
        this.drinkService = drinkService;
        this.testDataInit = testDataInit;
    }

    @Override
    public void run(String... args) {


//        userRoleService.initUserRoles();
//        userService.initAdmin();
//        userService.initUsers();
//        reviewService.initReviews();
//        foodService.initFoods();
//        drinkService.initDrinks();
//        orderService.initMenuOrder();

        testDataInit.initUserRoles();
        testDataInit.initAdmin();
        testDataInit.initUsers();
        testDataInit.initReviews();
        testDataInit.initFoods();
        testDataInit.initDrinks();
        testDataInit.initMenuOrder();


    }
}

