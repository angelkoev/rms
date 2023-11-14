package com.rms.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "drinks")
public class DrinkEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private int volume; //in ml

    //    @ManyToOne
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DrinkTypeEnum type;
//    private DrinkType type;
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private OrderEntity order;
    private int orderId;
    @Column(nullable = false)
    private int preparationTime;
    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false)
    private boolean delivered;
    private boolean isPaid; // may be not needed, because Order had the same field

    public DrinkEntity() {
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public DrinkTypeEnum getType() {
        return type;
    }

    public void setType(DrinkTypeEnum type) {
        this.type = type;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
