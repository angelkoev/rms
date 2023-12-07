package com.rms.service;

import com.rms.model.dto.RegisterDTO;
import com.rms.model.dto.UserDTO;
import com.rms.model.entity.*;
import com.rms.model.views.OrderView;
import com.rms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserByUsername() {
        // Mocking
        when(userRepositoryMock.findByUsername("john")).thenReturn(Optional.of(new UserEntity()));
        when(modelMapper.map(any(), any())).thenReturn(new UserDTO());

        // Test
        UserDTO userDTO = userServiceMock.findUserByUsername("john");

        assertNotNull(userDTO);
    }

    @Test
    public void testFindUserByEmail() {
        // Mocking
        when(userRepositoryMock.findByEmail("john@example.com")).thenReturn(Optional.of(new UserEntity()));
        when(modelMapper.map(any(), any())).thenReturn(new UserDTO());

        // Test
        UserDTO userDTO = userServiceMock.findUserByEmail("john@example.com");

        assertNotNull(userDTO);
    }

    @Test
    public void testRegister() {
        // Mocking
        when(modelMapper.map(any(), any())).thenReturn(new UserEntity());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRoleService.findUserRoleEntityByRole(UserRoleEnum.USER)).thenReturn(new UserRoleEntity());
        when(userRepositoryMock.save(any())).thenReturn(new UserEntity());

        // Test
        RegisterDTO registerDTO = new RegisterDTO("john", "john@example.com", "password", "John", "Doe", "123456", "Address");
        userServiceMock.register(registerDTO);
    }

}
