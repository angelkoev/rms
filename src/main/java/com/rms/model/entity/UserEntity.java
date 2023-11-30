package com.rms.model.entity;

import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

//    @UniqueUsername // FIXME
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
//    @UniqueEmail // FIXME
    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private String address;

    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER)
//    @Column(nullable = false)
    private Set<UserRoleEntity> roles;

    @OneToMany(mappedBy = "madeBy")
    private List<OrderEntity> orders;

    @OneToOne
    private OrderEntity lastOrder;

    public UserEntity() {
        this.roles = new HashSet<>();
        this.orders = new ArrayList<>();
        };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public OrderEntity getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(OrderEntity lastOrder) {
        this.lastOrder = lastOrder;
    }
}
