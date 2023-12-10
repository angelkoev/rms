package com.rms.service.Impl;

import com.rms.model.dto.DrinkDTO;
import com.rms.model.entity.DrinkEntity;
import com.rms.repository.DrinkRepository;
import com.rms.service.DrinkService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class DrinkServiceImpl implements DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkServiceImpl(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    @Override
    public boolean isDrinkAlreadyAdded(DrinkEntity drinkEntity) {
        Optional<DrinkEntity> findByName = drinkRepository.findByName(drinkEntity.getName());

        return findByName.isPresent();
    }

    @Override
    public boolean isDrinkAlreadyAdded(DrinkDTO drinkDTO) {
        Optional<DrinkEntity> findByName = drinkRepository.findByName(drinkDTO.getName());

        return findByName.isPresent();
    }

    @Override
    public void addDrink(DrinkEntity drinkEntity) {

        if (isDrinkAlreadyAdded(drinkEntity)) {
            throw new IllegalStateException();
        }

        drinkRepository.save(drinkEntity);
    }

    @Override
    public Set<DrinkEntity> findAllBy() {
        return drinkRepository.findAllBy();
    }

//    public Set<DrinkEntity> findAllByType(DrinkTypeEnum drinkTypeEnum) {
//        return drinkRepository.findAllByTypeEquals(drinkTypeEnum);
//    }

    @Override
    public DrinkEntity findById(Long id) {
        return drinkRepository.findById(id).orElse(null);
    }

}
