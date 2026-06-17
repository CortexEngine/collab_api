package com.example.collab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
public class CollabApplication {

	public static void main(String[] args) {

		SpringApplication.run(CollabApplication.class, args);

		System.err.println("Está rodando!");
		
	}

}
