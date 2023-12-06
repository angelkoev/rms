package com.rms.repository;

import com.rms.model.entity.AuthenticationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthenticationLogRepository extends JpaRepository<AuthenticationLog, Long> {

    List<AuthenticationLog> getAuthenticationLogByOrderByTimestampDesc();
}
