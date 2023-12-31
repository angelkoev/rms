package com.rms.repository;

import com.rms.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findOrderEntitiesById(Long id);

    List<OrderEntity> findOrderEntitiesByMadeBy_UsernameOrderByDateTimeDesc(String username);

    List<OrderEntity> getAllByOrderByDateTimeDesc();
}
