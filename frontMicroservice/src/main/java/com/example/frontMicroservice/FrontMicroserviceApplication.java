package com.example.frontMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FrontMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontMicroserviceApplication.class, args);
	}

}
