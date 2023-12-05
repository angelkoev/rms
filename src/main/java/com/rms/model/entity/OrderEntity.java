package com.rms.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @ManyToMany(fetch = FetchType.EAGER)
    private List<FoodEntity> foods;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<DrinkEntity> drinks;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private boolean isCompleted;

    @Column(nullable = false)
    private boolean isPaid;

    @ManyToOne
    private UserEntity madeBy;

    public OrderEntity() {
        this.foods = new ArrayList<>();
        this.drinks = new ArrayList<>();
    }

    public List<FoodEntity> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodEntity> foods) {
        this.foods = foods;
    }

    public List<DrinkEntity> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkEntity> drinks) {
        this.drinks = drinks;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public UserEntity getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(UserEntity madeBy) {
        this.madeBy = madeBy;
    }
}
