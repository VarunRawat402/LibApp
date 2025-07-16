package com.example.demo.config;

import com.example.demo.Enums.Authority;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF if necessary
                .httpBasic(Customizer.withDefaults()) // Enable Basic Authentication
                .formLogin(Customizer.withDefaults()) // Enable Form Login
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAuthority(Authority.ADMIN_AUTHORITY) // Restrict /admin/**
                        .requestMatchers(HttpMethod.POST, "/student/**").permitAll() // Allow all POST requests to /student
                        .requestMatchers("/student/**", "/txn/**").hasAuthority(Authority.STUDENT_AUTHORITY) // Only students can access
                        .requestMatchers("/book/filter/**", "/book/**")
                        .hasAnyAuthority(Authority.STUDENT_AUTHORITY, Authority.ADMIN_AUTHORITY) // Both roles can access books
                        .anyRequest().authenticated() // All other requests need authentication
                );
        return http.build();
    }
}
