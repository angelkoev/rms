package com.rms.service.Impl;

import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repository.UserRoleRepository;
import com.rms.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
    @Override
    public UserRoleEntity findUserRoleEntityByRole(UserRoleEnum userRoleEnum) {
        return userRoleRepository.findUserRoleEntityByRole(userRoleEnum);
    }


}

