package com.rms.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "foods")
public class Food extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private FoodType type;
    @Column(nullable = false)
    private BigDecimal size; //serving size
    @Column(nullable = false)
    private BigDecimal kcal; // calories

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @Column(nullable = false)
    private int preparationTime;
    @Column(nullable = false)
    private boolean completed;
    private boolean isPaid; // may be not needed, because Order had the same field

    public Food() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    public BigDecimal getKcal() {
        return kcal;
    }

    public void setKcal(BigDecimal kcal) {
        this.kcal = kcal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
