package com.rms.service.Impl;

import com.rms.model.AppUserDetails;
import com.rms.model.entity.UserEntity;
import com.rms.model.entity.UserRoleEntity;
import com.rms.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;


public class ApplicationUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return
                userRepository.
                        findByUsername(username).
                        map(this::map).
                        orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(UserEntity userEntity) {
        return new AppUserDetails(
                userEntity.getUsername(),
                userEntity.getPassword(),
                extractAuthorities(userEntity)
        ).
        setFullName(userEntity.getFirstName() + " " + userEntity.getLastName());
    }

    private Set<GrantedAuthority> extractAuthorities(UserEntity userEntity) {
        return userEntity
                .getRoles()
                .stream()
                .map(this::mapRole)
                .collect(Collectors.toSet());
    }

    private GrantedAuthority mapRole(UserRoleEntity userRoleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getRole().name());
    }

}
