package com.rms.repositiry;

import com.rms.model.entity.DrinkEntity;
import com.rms.model.entity.DrinkTypeEnum;
import com.rms.model.entity.FoodEntity;
import com.rms.model.entity.FoodTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

//    Set<FoodEntity> findAllByOrderById (Long id);
    Set<FoodEntity> findAllByOrderId (int id);

    Set<FoodEntity> findAllByTypeEqualsAndOrderId(FoodTypeEnum foodTypeEnum, int orderId);
}
