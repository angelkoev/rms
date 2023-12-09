package com.rms.service;

import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.FoodTypeEnum;
import com.rms.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public boolean isFoodAlreadyAdded(FoodEntity foodEntity) {
        Optional<FoodEntity> findByName = foodRepository.findByName(foodEntity.getName());

        return findByName.isPresent();
    }

    public boolean isFoodAlreadyAdded(FoodDTO foodDTO) {
        Optional<FoodEntity> findByName = foodRepository.findByName(foodDTO.getName());

        return findByName.isPresent();
    }

    public void addFood(FoodEntity foodEntity) {

        if (isFoodAlreadyAdded(foodEntity)) {
            throw new IllegalStateException();
        }

        foodRepository.save(foodEntity);
    }

    public Set<FoodEntity> findAllBy () {
        return foodRepository.findAllBy();
    }

//    public Set<FoodEntity> findAllByType(FoodTypeEnum foodTypeEnum) {
//        return foodRepository.findAllByTypeEquals(foodTypeEnum);
//    }

    public FoodEntity findById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }
}
