package com.rms.config;

import com.rms.model.entity.UserRoleEnum;
import com.rms.repository.UserRepository;
import com.rms.service.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
//@EnableAspectJAutoProxy
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/", "/index", "/users/login", "/users/register", "/users/login-error", "/reviews/all", "/api/reviews",
                                                "/order", "/order/menu", "/order/drink/**", "/order/food/**", "/maintenance", "/error").permitAll()
                                        .requestMatchers("/views/admins", "/order/drink/**", "/order/food/**", "/maintenance/**", "/users/**", "/order/add/**", "/logs/**").hasRole(UserRoleEnum.ADMIN.name())
                                        .requestMatchers("/order/menu", "/order/drink/**", "/order/food/**", "/maintenance").hasRole(UserRoleEnum.USER.name())
                                        .anyRequest().authenticated()

                )
                .formLogin(
                        (formLogin) ->
                                formLogin
                                        .loginPage("/users/login")
                                        .usernameParameter("username")
                                        .passwordParameter("password")
                                        .defaultSuccessUrl("/", true)
                                        .failureUrl("/users/login-error")
                )
                .logout((logout) ->
                        logout
                                .logoutUrl("/users/logout") // The URL to trigger the logout process
                                .logoutSuccessUrl("/") // Redirect to the homepage after logout
                                .invalidateHttpSession(true) // Invalidate the user's session
                                .deleteCookies("JSESSIONID") // Optionally, delete cookies, e.g., JSESSIONID
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetailsService(userRepository);
    }


}
