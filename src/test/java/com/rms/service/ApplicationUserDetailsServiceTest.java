package com.rms.service;

import static org.junit.jupiter.api.Assertions.*;

import com.rms.model.AppUserDetails;
import com.rms.model.entity.UserEntity;
import com.rms.model.entity.UserRoleEntity;
import com.rms.model.entity.UserRoleEnum;
import com.rms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

public class ApplicationUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        // Arrange
        String username = "testUser";
        UserEntity userEntity = createUserEntity(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertTrue(userDetails instanceof AppUserDetails);
        AppUserDetails appUserDetails = (AppUserDetails) userDetails;
        assertEquals(username, appUserDetails.getUsername());
        assertEquals(userEntity.getPassword(), appUserDetails.getPassword());
        assertEquals("John Doe", appUserDetails.getFullName());
        assertEquals(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), appUserDetails.getAuthorities());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonExistingUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
    }

    private UserEntity createUserEntity(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword("password");
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(UserRoleEnum.USER);
        userRoleEntity.setDescription("Just user");

        Set<UserRoleEntity> roles = new HashSet<>();
        roles.add(userRoleEntity);

        userEntity.setRoles(roles);

        return userEntity;
    }
}