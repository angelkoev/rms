package com.rms.service;

import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;

public interface UserRoleService {
    UserRoleEntity findUserRoleEntityByRole(UserRoleEnum userRoleEnum);
}
