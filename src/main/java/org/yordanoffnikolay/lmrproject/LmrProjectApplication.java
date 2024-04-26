package org.yordanoffnikolay.lmrproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("org.yordanoffnikolay.lmrproject.models")
public class LmrProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmrProjectApplication.class, args);
    }

}
