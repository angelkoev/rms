package com.rms.model.dto;

import com.rms.validation.annotation.UniqueEmail;
import com.rms.validation.annotation.UniqueUsername;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

public class RegisterDTO {
    private Long id;

    @UniqueUsername
    @Size(min = 3, max = 20, message = "Потребителското име трябва да е между 3 и 20 символа!")
    @NotBlank(message = "моля, въведете потребителско име")
    private String username;

    @UniqueEmail
    @Email (message = "въведете валиден email")
    @NotBlank(message = "моля, въведете email")
    private String email;

    private String firstName;
    private String lastName;
    private String address;

    @NotBlank(message = "моля, въведете телефонен номер")
    @Pattern(regexp="|(\\+?[0-9 ]{6,})", message = "моля, въведете валиден телефонен номер (минимум 6 цифри)")
    private String phone;
    private LocalDate registrationDate;

    @Size(min = 3, max = 20, message = "Паролата трябва да е между 3 и 20 символа")
    @NotNull
    private String password;

//    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    @NotNull
    private String confirmPassword;

    public RegisterDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
