package com.rms.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drink_types")
public class DrinkType extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private DrinkTypeEnum name;
    @Column(nullable = false)
    private String description;

    public DrinkType() {
    }

    public DrinkTypeEnum getName() {
        return name;
    }

    public void setName(DrinkTypeEnum name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
