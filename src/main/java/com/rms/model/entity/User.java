package com.rms.model.entity;

import com.rms.validation.annotation.UniqueEmail;
import com.rms.validation.annotation.UniqueUsername;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @UniqueUsername
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @UniqueEmail
    @Column(nullable = false, unique = true)
    private String email;

    private String firstName;
    private String lastName;
    private String address;

    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private LocalDate registrationDate;

    @ManyToOne
//    @Column(nullable = false)
    private UserRole role;

    public User() {
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
