package com.perso.bio;

import com.perso.bio.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class BioApplication implements CommandLineRunner {


    JwtService jwtService;

    @Autowired
    public BioApplication(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BioApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.jwtService.removeCurrentJwt();
    }



}
