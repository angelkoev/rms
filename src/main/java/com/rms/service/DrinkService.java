package com.rms.service;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.entity.DrinkEntity;

import java.util.Set;

public interface DrinkService {

    boolean isDrinkAlreadyAdded(DrinkEntity drinkEntity);

    boolean isDrinkAlreadyAdded(DrinkDTO drinkDTO);

    void addDrink(DrinkEntity drinkEntity);

    Set<DrinkEntity> findAllBy();

    DrinkEntity findById(Long id);
}
