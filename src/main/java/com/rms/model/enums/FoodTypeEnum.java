package com.rms.model.enums;

public enum FoodTypeEnum {
    SALAD("Салати"),
    SOUP("Супи"),
    DESSERT("Десерти"),
    MAIN_DISH("Основни"),
    SANDWICH("Сандиви"),
    PIZZA("Пици"),
    BBQ("Скара"),
    VEGETARIAN("Вегетариански"),
    BREAD("Тестени");

    private final String name;

    FoodTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    }
