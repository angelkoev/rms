package com.rms.repository;

import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.FoodTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    Set<FoodEntity> findAllBy();

    Set<FoodEntity> findAllByTypeEquals(FoodTypeEnum foodTypeEnum);

    Optional<FoodEntity> findById(Long id);

    Optional<FoodEntity> findByName(String name);
}
