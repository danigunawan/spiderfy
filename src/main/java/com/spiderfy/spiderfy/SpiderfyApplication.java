package com.spiderfy.spiderfy;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.spiderfy.*"})
public class SpiderfyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiderfyApplication.class, args);

    }
}
