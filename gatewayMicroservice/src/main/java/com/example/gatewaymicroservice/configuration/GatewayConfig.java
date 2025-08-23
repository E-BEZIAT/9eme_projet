package com.example.gatewaymicroservice.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patientMicroservice", r -> r
                        .path("/validate/**", "/api/**", "/user/***")
                        .uri("http://localhost:8081"))
                .route("medecinMicroservice", r -> r
                        .path("/medecin/**")
                        .uri("http://localhost:8082"))
                .route("frontMicroservice", r -> r
                        .path("/login", "/home", "/register", "/user/**", "/patient/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}
