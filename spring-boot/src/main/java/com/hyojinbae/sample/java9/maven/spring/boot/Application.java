package com.hyojinbae.sample.java9.maven.spring.boot;

import com.hyojinbae.sample.java9.maven.core.Dummy;
import com.hyojinbae.sample.java9.maven.module1.ModuleClass1;
import com.hyojinbae.sample.java9.maven.module2.ModuleClass2;
import com.hyojinbae.sample.java9.maven.nonmodule.NonModuleClass;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Java9のモジュールの可視性を試してみる");

            Stream.of(
                    Dummy.of("Dummy"),
                    NonModuleClass.of("NonModuleClass"),
                    ModuleClass1.of("ModuleClass1"),
                    ModuleClass2.of("ModuleClass2")
            )
            .forEach(System.out::println);
        };
    }
}
