package com.rms.repository;

import com.rms.model.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

    List<LogEntity> getAuthenticationLogByOrderByTimestampDesc();
}
