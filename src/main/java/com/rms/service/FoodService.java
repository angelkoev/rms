package com.rms.service;

import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.FoodEntity;

import java.util.Set;

public interface FoodService {
    boolean isFoodAlreadyAdded(FoodEntity foodEntity);

    boolean isFoodAlreadyAdded(FoodDTO foodDTO);

    void addFood(FoodEntity foodEntity);

    Set<FoodEntity> findAllBy();

    //    public Set<FoodEntity> findAllByType(FoodTypeEnum foodTypeEnum) {
    //        return foodRepository.findAllByTypeEquals(foodTypeEnum);
    //    }
    FoodEntity findById(Long id);
}
