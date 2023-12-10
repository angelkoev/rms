package com.rms.service;

import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repository.UserRoleRepository;
import com.rms.service.Impl.UserRoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleServiceImpl userRoleServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserRoleEntityByRole_AdminRoleExists() {
        // Arrange
        UserRoleEnum adminRole = UserRoleEnum.ADMIN;
        UserRoleEntity adminUserRoleEntity = new UserRoleEntity();
        adminUserRoleEntity.setRole(adminRole);
        adminUserRoleEntity.setDescription("The owner of the company");
        when(userRoleRepository.findUserRoleEntityByRole(adminRole)).thenReturn(adminUserRoleEntity);

        // Act
        UserRoleEntity result = userRoleServiceImpl.findUserRoleEntityByRole(adminRole);

        // Assert
        assertEquals(adminUserRoleEntity, result);
    }

    @Test
    public void testFindUserRoleEntityByRole_NonExistingRole() {
        // Arrange
        UserRoleEnum nonExistingRole = UserRoleEnum.USER;
        when(userRoleRepository.findUserRoleEntityByRole(nonExistingRole)).thenReturn(null);

        // Act
        UserRoleEntity result = userRoleServiceImpl.findUserRoleEntityByRole(nonExistingRole);

        // Assert
        assertNull(result);
    }

}