package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class DemoApplication {
    static String url = new String();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}

