package com.rms.repositiry;

import com.rms.model.entity.DrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkEntity, Long> {

//    Set<DrinkEntity> findAllByOrderById (Long id);
    Set<DrinkEntity> findAllByOrderId (int id);
}
