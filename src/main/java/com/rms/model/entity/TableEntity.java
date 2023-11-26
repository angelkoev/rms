//package com.rms.model.entity;
//
//import jakarta.persistence.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "tables")
//public class TableEntity extends BaseEntity {
//
//    @Column(nullable = false)
//    private int capacity;
////    @Enumerated(EnumType.STRING)
////    @Column(nullable = false)
////    private LocationEnum location;
////    @Column(nullable = false)
////    private boolean isForSmoking;
//    @Column(nullable = false)
//    private boolean isReserved;
//
//    @OneToMany (mappedBy = "table")
//    private Set<OrderEntity> orders;
//
//    @ManyToOne
//    private UserEntity waiter;
//
//    public TableEntity() {
//        this.orders = new HashSet<>();
//    }
//
//    public int getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(int capacity) {
//        this.capacity = capacity;
//    }
//
////    public LocationEnum getLocation() {
////        return location;
////    }
//
////    public void setLocation(LocationEnum location) {
////        this.location = location;
////    }
//
////    public boolean isForSmoking() {
////        return isForSmoking;
////    }
////
////    public void setForSmoking(boolean forSmoking) {
////        isForSmoking = forSmoking;
////    }
//
//    public boolean isReserved(boolean b) {
//        return isReserved;
//    }
//
//    public void setReserved(boolean reserved) {
//        isReserved = reserved;
//    }
//
//    public Set<OrderEntity> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(Set<OrderEntity> order) {
//        this.orders = order;
//    }
//
//    public UserEntity getWaiter() {
//        return waiter;
//    }
//
//    public void setWaiter(UserEntity waiter) {
//        this.waiter = waiter;
//    }
//}
