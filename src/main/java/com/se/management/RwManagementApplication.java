package com.se.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RwManagementApplication  {

    public static void main(String[] args) {
        SpringApplication.run(RwManagementApplication.class, args);
    } 
}

