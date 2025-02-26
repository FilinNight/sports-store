package com.company.store;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(info = @Info(
        title = "Sports-Store API",
        version = "1.0",
        description = "Documentation Sports-Store API v1.0"
))
@SpringBootApplication
@EnableJpaAuditing  /* Включает аудит */
public class SportsStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsStoreApplication.class, args);
    }

}
