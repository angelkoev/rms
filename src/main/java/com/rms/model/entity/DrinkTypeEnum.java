package com.rms.model.entity;

public enum DrinkTypeEnum {
    COFFEE_BASED("Кафе напитки"),
    WINE("Вино"),
    HOT_BEVERAGE("Горещи напитки"),
    JUICE("Сок"),
    BEER("Бира"),
    SODA("Безалкохолни"),
    COCKTAIL("Коктейли"),
    ALCOHOLIC("Алкохол");

    private final String name;

    DrinkTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

