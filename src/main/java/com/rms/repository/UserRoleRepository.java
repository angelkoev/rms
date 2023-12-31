package com.rms.repository;

import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity  findUserRoleEntityByRole(UserRoleEnum userRoleEnum);
}
