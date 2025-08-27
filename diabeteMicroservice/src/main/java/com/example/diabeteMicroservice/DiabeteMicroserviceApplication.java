package com.example.diabeteMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DiabeteMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiabeteMicroserviceApplication.class, args);
	}

}
