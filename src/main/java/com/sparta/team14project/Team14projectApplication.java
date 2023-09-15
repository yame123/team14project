package com.sparta.team14project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Team14projectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team14projectApplication.class, args);
    }

}
