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

import java.util.List;
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
//                setCountry(userEntity.getCountry()).
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

//    private List<GrantedAuthority> extractAuthorities(UserEntity userEntity) {
//        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
//        for (UserRoleEntity u : userEntity.getRoles()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + u.getName().name()));
//        }
//        return grantedAuthorities; // FIXME only names of the roles or ??
////        return List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getName().name()));
//    }


}
