package com.rms.service;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.DrinkTypeEnum;
import com.rms.repository.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public boolean isDrinkAlreadyAdded(DrinkEntity drinkEntity) {
        Optional<DrinkEntity> findByName = drinkRepository.findByName(drinkEntity.getName());

        return findByName.isPresent();
    }

    public boolean isDrinkAlreadyAdded(DrinkDTO drinkDTO) {
        Optional<DrinkEntity> findByName = drinkRepository.findByName(drinkDTO.getName());

        return findByName.isPresent();
    }

    public void addDrink(DrinkEntity drinkEntity) {

        if (isDrinkAlreadyAdded(drinkEntity)) {
            throw new IllegalStateException();
        }

        drinkRepository.save(drinkEntity);
    }

    public Set<DrinkEntity> findAllBy() {
        return drinkRepository.findAllBy();
    }

    public Set<DrinkEntity> findAllByType(DrinkTypeEnum drinkTypeEnum) {
        return drinkRepository.findAllByTypeEquals(drinkTypeEnum);
    }

    public DrinkEntity findById(Long id) {
        return drinkRepository.findById(id).orElse(null);
    }

}
