package com.rms.model.entity;

public enum DrinkTypeEnum {
    COFFEE_BASED("Кафе напитки"),
    WINE("Вина"),
    HOT_BEVERAGE("Горещи напитки"),
    JUICE("Сокове"),
    BEER("Бири"),
    SODA("Безалкохолни"),
    COCKTAIL("Коктейли"),
    ALCOHOLIC("Алкохолни");

    private final String name;

    DrinkTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

