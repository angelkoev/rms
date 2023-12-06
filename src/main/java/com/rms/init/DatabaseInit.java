package com.rms.init;

//import com.rms.service.DrinkTypeServiceImpl;
//import com.rms.service.FoodTypeServiceImpl;

//import com.rms.service.interfaces.DrinkTypeService;
//import com.rms.service.interfaces.FoodTypeService;
//import com.rms.service.interfaces.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Order(0)
@Component
public class DatabaseInit implements CommandLineRunner {

    private final InitialDataInit initialDataInit;

    public DatabaseInit(InitialDataInit initialDataInit) {

        this.initialDataInit = initialDataInit;
    }

    @Override
    public void run(String... args) {

        initialDataInit.initUserRoles();
        initialDataInit.initAdmin();
        initialDataInit.initUsers();
        initialDataInit.initReviews();
        initialDataInit.initFoods();
        initialDataInit.initDrinks();
        initialDataInit.initMenuOrder();


    }
}

