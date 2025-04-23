package com.fangxia.testvalidator.common.config;

import com.fangxia.testvalidator.common.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/fxedu/api/test/**",
                        "/fxedu/api/auth/send-verify-code",
                        "/fxedu/api/auth/register",
                        "/fxedu/api/auth/login",
                        "/fxedu/api/auth/logout",
                        "/fxedu/api/auth/refresh"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
