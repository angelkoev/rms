package com.rms.repository;

import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.DrinkTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkEntity, Long> {

    //    Set<DrinkEntity> findAllByOrderById (Long id);
    Set<DrinkEntity> findAllBy();

    Set<DrinkEntity> findAllByTypeEquals(DrinkTypeEnum drinkTypeEnum);

    Optional<DrinkEntity> findById(Long id);

    Optional<DrinkEntity> findByName(String name);
}
