package com.example.gatewaymicroservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class LoginController {

    private final WebClient webClient;

    public LoginController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
    }

    @GetMapping("/login")
    public Mono<ResponseEntity<String>> loginRedirect() {
        return webClient.get()
                .uri("http://localhost:8083/login") // front
                .retrieve()
                .bodyToMono(String.class)
                .map(html -> ResponseEntity.ok().body(html));
    }
}
