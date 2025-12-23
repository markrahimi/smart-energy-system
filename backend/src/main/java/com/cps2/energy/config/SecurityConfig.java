package com.cps2.energy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  //TODO: CSRF is deactive for test after test change this
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // TODO: Any Request have permission for test after test change this
            );

        return http.build();
    }
}
