package com.rms.model.views;

import com.rms.model.entity.DrinkTypeEnum;

import java.math.BigDecimal;

public class DrinkView {

    private Long id;
    private String name;
    private BigDecimal price;
    private int volume; //in ml

    private DrinkTypeEnum type;

    public DrinkView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
