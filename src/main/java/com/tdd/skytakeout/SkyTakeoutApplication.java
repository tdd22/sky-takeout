package com.tdd.skytakeout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SkyTakeoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyTakeoutApplication.class, args);
    }

}
