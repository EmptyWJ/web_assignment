package com.ourwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@SpringBootApplication
public class ResetApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResetApplication.class, args);
    }
}
