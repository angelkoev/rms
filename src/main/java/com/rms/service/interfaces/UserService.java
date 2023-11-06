package com.rms.service.interfaces;

import com.rms.model.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findById(Long id);

    void addAdmin();

    void addEmployees();

    void AddClients();


//    Optional<User> findById(Long id);
//
//    Optional<User> findByUsername(String username);
//    Optional<User> findByEmail(String email);
}
