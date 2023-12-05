package com.rms.service;

import com.rms.model.entity.UserRoleEntity;
import com.rms.model.enums.UserRoleEnum;
import com.rms.repositiry.UserRoleRepository;
//import com.rms.service.interfaces.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRoleService
//        implements UserRoleService
{

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

//    @Override
    public void initUserRoles() {
        if (userRoleRepository.count() != 0) {
            return;
        }

        Arrays.stream(UserRoleEnum.values())
                .forEach(userRoleEnum -> {
                    UserRoleEntity userRole = new UserRoleEntity();
                    userRole.setRole(userRoleEnum);

                    switch (userRoleEnum) {
                        case ADMIN -> userRole.setDescription("The owner of the company");
                        case USER -> userRole.setDescription("general user");
//                        case WAITER -> userRole.setDescription("handle the order from the client");
//                        case COOK -> userRole.setDescription("cook the food");
//                        case BARTENDER -> userRole.setDescription("make the drinks/cocktails");
//                        case CLIENT -> userRole.setDescription("client - make order from the menu");
//                        case CLEANER -> userRole.setDescription("clean the place from the mess");
                    }

                    userRoleRepository.save(userRole);
                });
    }

//    @Override
    public UserRoleEntity findUserRoleEntityByRole(UserRoleEnum userRoleEnum) {
        return userRoleRepository.findUserRoleEntityByRole(userRoleEnum);
    }


}

