package com.example.be_velog_team11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BeVelogTeam11Application {

    public static void main(String[] args) {
        SpringApplication.run(BeVelogTeam11Application.class, args);
    }

}
