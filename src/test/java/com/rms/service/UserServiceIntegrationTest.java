package com.rms.service;

import com.rms.model.entity.UserEntity;
import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repository.UserRepository;
import com.rms.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    @InjectMocks
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void contextLoads() {
        assertNotNull(userRoleRepository);
    }

    @Test
    public void testGetUserByUsername() {
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail("testmail@test.test");
        userEntity.setPassword("testpassword");
        userEntity.setPhone("11111111");
        UserRoleEntity userRole = userRoleRepository.findUserRoleEntityByRole(UserRoleEnum.USER);
        userEntity.setRoles(Set.of(userRole));
        userEntity.setRegistrationDate(LocalDate.now());

        userRepository.save(userEntity);

        UserEntity retrievedUser = userService.getUserByUsername(username);

        assertNotNull(retrievedUser);
        assertEquals(username, retrievedUser.getUsername());

    }
}