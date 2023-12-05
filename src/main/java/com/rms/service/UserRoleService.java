package com.rms.service;

import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repositiry.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRoleEntity findUserRoleEntityByRole(UserRoleEnum userRoleEnum) {
        return userRoleRepository.findUserRoleEntityByRole(userRoleEnum);
    }


}

