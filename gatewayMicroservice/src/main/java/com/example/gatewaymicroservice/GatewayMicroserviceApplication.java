package com.example.gatewaymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GatewayMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMicroserviceApplication.class, args);
    }

}
