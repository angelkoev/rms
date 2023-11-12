package com.rms.config;

import com.rms.model.entity.UserRoleEnum;
import com.rms.repositiry.UserRepository;
import com.rms.service.ApplicationUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {

    //    private final String rememberMeKey;
//
//    public SecurityConfiguration(@Value("${rms.remember.me.key}")
//                                 String rememberMeKey) {
//        this.rememberMeKey = rememberMeKey;
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                authorizeHttpRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/", "/index", "/users/login", "/users/register", "/users/login-error").permitAll()
                                        .requestMatchers("/views/admins").hasRole(UserRoleEnum.ADMIN.name())
                                        .requestMatchers("/views/waiters").hasRole(UserRoleEnum.WAITER.name())
                                        .requestMatchers("/views/cooks").hasRole(UserRoleEnum.COOK.name())
                                        .requestMatchers("/views/bartenders").hasRole(UserRoleEnum.BARTENDER.name())
                                        .requestMatchers("/views/clients").hasRole(UserRoleEnum.CLIENT.name())
                                        .anyRequest().authenticated()

                )
                .formLogin(
                        (formLogin) ->
                                formLogin
                                        .loginPage("/users/login")
//                                        usernameParameter(
//                                                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
//                                        passwordParameter(
//                                                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
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
//                    .rememberMe(
//                rememberMe -> {
//                    rememberMe
//                            .key(rememberMeKey)
//                            .rememberMeParameter("remember-me")
//                            .rememberMeCookieName("remember-me");
//                });

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

//    @Bean
//    public SecurityContextRepository securityContextRepository() {
//        return new DelegatingSecurityContextRepository(
//                new RequestAttributeSecurityContextRepository(),
//                new HttpSessionSecurityContextRepository()
//        );
//    }

}
