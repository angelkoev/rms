package com.rms.model.dto;

import com.rms.model.entity.DrinkTypeEnum;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class DrinkDTO {
    @Size(min = 3, max = 20, message = "Името трябва да е между 3 и 20 символа")
    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0.00", message = "цената трябва да е по-голяма от 0")
    @DecimalMax(value = "99999.99", message = "цената трябва да е по-малка от 99999.99")
    private BigDecimal price;

    @NotNull
    @Min(value = 1, message = "обема на напитката трябва да е по-голям от 0мл")
    @Max(value = 5000, message = "обема на напитката трябва да е по-малък от 5000мл")
    private int volume; //in ml

    @NotNull (message = "Изберете тип на напитката:")
    private DrinkTypeEnum type;

//    @NotNull
    private boolean addToMenu;

    public DrinkDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public DrinkTypeEnum getType() {
        return type;
    }

    public void setType(DrinkTypeEnum type) {
        this.type = type;
    }

    public boolean isAddToMenu() {
        return addToMenu;
    }

    public void setAddToMenu(boolean addToMenu) {
        this.addToMenu = addToMenu;
    }
}
