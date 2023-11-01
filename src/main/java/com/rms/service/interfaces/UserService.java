package com.rms.service.interfaces;

import com.rms.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);



//    Optional<User> findById(Long id);
//
//    Optional<User> findByUsername(String username);
//    Optional<User> findByEmail(String email);
}
