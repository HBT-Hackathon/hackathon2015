package de.hbt.hackathon2015;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class Hackathon2015Application {

    public static void main(String[] args) {
        SpringApplication.run(Hackathon2015Application.class, args);
    }
}
