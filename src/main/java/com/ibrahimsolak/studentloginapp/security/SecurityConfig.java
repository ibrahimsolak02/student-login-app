package com.ibrahimsolak.studentloginapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibrahimsolak.studentloginapp.security.filter.AuthenticationFilter;
import com.ibrahimsolak.studentloginapp.security.filter.ExceptionHandlerFilter;
import com.ibrahimsolak.studentloginapp.security.filter.JWTAuthorizationFilter;
import com.ibrahimsolak.studentloginapp.security.manager.CustomAuthenticationManager;
import com.ibrahimsolak.studentloginapp.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();

    }
    }
