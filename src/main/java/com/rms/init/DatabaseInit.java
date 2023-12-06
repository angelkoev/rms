package com.rms.init;

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

    private final TestDataInit testDataInit;

    public DatabaseInit(TestDataInit testDataInit) {

        this.testDataInit = testDataInit;
    }

    @Override
    public void run(String... args) {

        testDataInit.initUserRoles();
        testDataInit.initAdmin();
        testDataInit.initUsers();
        testDataInit.initReviews();
        testDataInit.initFoods();
        testDataInit.initDrinks();
        testDataInit.initMenuOrder();


    }
}

