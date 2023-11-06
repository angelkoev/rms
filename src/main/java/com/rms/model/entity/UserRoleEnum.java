package com.rms.model.entity;

public enum UserRoleEnum {
    ADMIN ("The owner of the company"),
    WAITER ("handle the order from the client"),
    COOK ("cook the food"),
    BARTENDER ("make the drinks/cocktails"),
    USER("client - can make order from the menu"),
    CLEANER ("clean the place from the mess");

    private final String description;
    UserRoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
