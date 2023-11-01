package com.rms.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tables")
public class TableEntity extends BaseEntity {

    @Column(nullable = false)
    private int capacity;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private LocationEnum location;
    @Column(nullable = false)
    private boolean isForSmoking;
    @Column(nullable = false)
    private boolean isReserved;

    @OneToMany (mappedBy = "table")
    private Set<Order> order;

    @OneToOne
    private User waiter;

    public TableEntity() {
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

//    public LocationEnum getLocation() {
//        return location;
//    }

//    public void setLocation(LocationEnum location) {
//        this.location = location;
//    }

    public boolean isForSmoking() {
        return isForSmoking;
    }

    public void setForSmoking(boolean forSmoking) {
        isForSmoking = forSmoking;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }

    public User getWaiter() {
        return waiter;
    }

    public void setWaiter(User waiter) {
        this.waiter = waiter;
    }
}
