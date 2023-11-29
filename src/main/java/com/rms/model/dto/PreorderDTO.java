package com.rms.model.dto;

import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;

import java.util.HashSet;
import java.util.Set;

public class PreorderDTO {
//    Long id;
    private Set<FoodView> foods;
    private Set<DrinkView> drinks;

    public PreorderDTO() {
        this.foods = new HashSet<>();
        this.drinks = new HashSet<>();
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Set<FoodView> getFoods() {
        return foods;
    }

    public void setFoods(Set<FoodView> foods) {
        this.foods = foods;
    }

    public Set<DrinkView> getDrinks() {
        return drinks;
    }

    public void setDrinks(Set<DrinkView> drinks) {
        this.drinks = drinks;
    }
}
