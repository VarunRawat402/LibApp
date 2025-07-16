package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 Why we did not include this in security config?
 Security config is using UserDetails Service so, it is dependent on that
 and UserDetailsService also using password encoder so if we add the password
 encoder in security config it will become circular dependency
 */

@Configuration
public class CommonConfig {
    @Bean
    PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
