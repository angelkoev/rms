package com.rms.model.dto;

import com.rms.model.entity.FoodTypeEnum;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class FoodDTO {
    @Size(min = 3, max = 20, message = "Името трябва да е между 3 и 20 символа")
    @NotNull
    private String name;
    @NotNull
//    @Pattern(regexp="^\\d{0,5}(\\.\\d{1,2})?$", message = "Въведената цена не е валидна")
    @DecimalMin(value = "0.00", inclusive = false, message = "Цената трябва да е по-голяма от 0.00")
    @DecimalMax(value = "99999.99", message = "Цената трябва да е по-малка от 99999.99")
    private BigDecimal price;

    @NotNull (message = "Изберете тип на храната:")
    private FoodTypeEnum type;

    @NotNull
    @Min(value = 1, message = "Големината на порцията трябва да е по-голяма от 0 гр")
    @Max(value = 5000, message = "Големината на порцията трябва да е по-малка от 5000 гр")
    private int size;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false, message = "Калориите трябва да са повече от 0.00")
    @DecimalMax(value = "99999.99", message = "Калориите трябва да са под 99999.99")
    private BigDecimal kcal;

    private boolean addToMenu;

    public FoodDTO() {
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

    public FoodTypeEnum getType() {
        return type;
    }

    public void setType(FoodTypeEnum type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BigDecimal getKcal() {
        return kcal;
    }

    public void setKcal(BigDecimal kcal) {
        this.kcal = kcal;
    }

    public boolean isAddToMenu() {
        return addToMenu;
    }

    public void setAddToMenu(boolean addToMenu) {
        this.addToMenu = addToMenu;
    }
}
