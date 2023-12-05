package com.rms.model.entity;

public enum UserRoleEnum {
    ADMIN ("The owner of the company"),
    USER("Just user");

    private final String description;
    UserRoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
