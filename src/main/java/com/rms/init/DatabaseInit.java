package com.rms.init;

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

