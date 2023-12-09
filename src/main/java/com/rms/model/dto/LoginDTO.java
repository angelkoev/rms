package com.rms.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    private Long id;

    @Size(min = 3, max = 20, message = "Потребителското име трябва да е между 3 и 20 символа!")
    @NotNull
    private String username;

    @Size(min = 3, max = 20, message = "Паролата трябва да е между 3 и 20 символа!")
    @NotNull
    private String password;

    public LoginDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}