package com.rms.service.impl;

import com.rms.model.AppUserDetails;
import com.rms.model.entity.UserEntity;
import com.rms.model.entity.UserRoleEntity;
import com.rms.repositiry.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

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
//                setCountry(userEntity.getCountry()).
        setFullName(userEntity.getFirstName() + " " + userEntity.getLastName());
    }

    private List<GrantedAuthority> extractAuthorities(UserEntity userEntity) {
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
        for (UserRoleEntity u : userEntity.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(u.getName().name()));
        }
        return grantedAuthorities;
//        return List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getName().name()));
    }


}
