package com.hyojinbae.sample.java9.maven.spring.boot;

import com.hyojinbae.sample.java9.maven.module1.ModuleClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        ModuleClass.of("New API", " IN ", "Java9")
                .add("GOOD!");
//                .forEach(System.out::print);
    }
}
