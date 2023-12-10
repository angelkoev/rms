package com.rms.service.Impl;

import com.rms.model.dto.FoodDTO;
import com.rms.model.entity.FoodEntity;
import com.rms.repository.FoodRepository;
import com.rms.service.FoodService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public boolean isFoodAlreadyAdded(FoodEntity foodEntity) {
        Optional<FoodEntity> findByName = foodRepository.findByName(foodEntity.getName());

        return findByName.isPresent();
    }

    @Override
    public boolean isFoodAlreadyAdded(FoodDTO foodDTO) {
        Optional<FoodEntity> findByName = foodRepository.findByName(foodDTO.getName());

        return findByName.isPresent();
    }

    @Override
    public void addFood(FoodEntity foodEntity) {

        if (isFoodAlreadyAdded(foodEntity)) {
            throw new IllegalStateException();
        }

        foodRepository.save(foodEntity);
    }

    @Override
    public Set<FoodEntity> findAllBy() {
        return foodRepository.findAllBy();
    }

    //    public Set<FoodEntity> findAllByType(FoodTypeEnum foodTypeEnum) {
//        return foodRepository.findAllByTypeEquals(foodTypeEnum);
//    }
    @Override
    public FoodEntity findById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }
}
