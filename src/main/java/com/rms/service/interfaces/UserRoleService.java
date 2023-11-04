package com.rms.service.interfaces;

import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;

public interface UserRoleService {

    void initUserRoles();

    UserRoleEntity findUserRoleEntityByRole(UserRoleEnum userRoleEnum);
}
