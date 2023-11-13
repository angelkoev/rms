//package com.rms.model.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "food_types")
//public class FoodType extends BaseEntity {
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false, unique = true)
//    private FoodTypeEnum name;
//    @Column(nullable = false)
//    private String description;
//
//    public FoodType() {
//    }
//
//    public FoodTypeEnum getName() {
//        return name;
//    }
//
//    public void setName(FoodTypeEnum name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
