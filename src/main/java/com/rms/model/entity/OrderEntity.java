package com.rms.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @OneToMany(fetch = FetchType.EAGER)
    private Set<FoodEntity> foods;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<DrinkEntity> drinks;
    @ManyToOne
    private TableEntity table;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private boolean isCompleted;

    @Column(nullable = false)
    private boolean isPaid;

    public OrderEntity() {
        this.foods = new HashSet<>();
        this.drinks = new HashSet<>();
    }

    public Set<FoodEntity> getFoods() {
        return foods;
    }

    public void setFoods(Set<FoodEntity> foods) {
        this.foods = foods;
    }

    public Set<DrinkEntity> getDrinks() {
        return drinks;
    }

    public void setDrinks(Set<DrinkEntity> drinks) {
        this.drinks = drinks;
    }

    public TableEntity getTable() {
        return table;
    }

    public void setTable(TableEntity table) {
        this.table = table;
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
}
