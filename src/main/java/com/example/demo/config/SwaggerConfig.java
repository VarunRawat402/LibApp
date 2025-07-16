package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi(){
        return new OpenAPI()
                .info(
                        new Info().title("Library Management APIs").description("By Varun Rawat")
                )
                .tags(Arrays.asList(
                        new Tag().name("Student APIs"),
                        new Tag().name("Book APIs"),
                        new Tag().name("Transaction APIs"),
                        new Tag().name("Admin APIs")
                ));
    }
}
