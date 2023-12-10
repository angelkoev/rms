package com.rms.service;

import com.rms.model.dto.RegisterDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.UserEntity;
import com.rms.model.views.DrinkView;
import com.rms.model.views.FoodView;
import com.rms.model.views.UserView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO findUserByUsername(String username);

    UserEntity findUserEntityByUsername(String username);

    boolean isAdmin(String username);

    void checkLastOrder(String username);

    List<DrinkView> getAllCurrentDrinkViews(String username);

    List<FoodView> getAllCurrentFoodViews(String username);

    void register(RegisterDTO registerDTO);

    UserEntity getUserByUsername(String username);

    Optional<UserEntity> findById(Long id);

    Long usersCount();


    void addAdmin(@AuthenticationPrincipal UserDetails userDetails, Long id);

    void removeAdmin(Long id);
}
