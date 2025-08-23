package com.example.gatewaymicroservice.configuration;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/login", "/register").permitAll()
                        .anyExchange().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .authenticationManager(auth -> {
                            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
                            String username = token.getName();
                            String password = (String) token.getCredentials();

                            return webClient().post()
                                    .uri("http://localhost:8081/api/login/validate-user")
                                    .body(BodyInserters.fromFormData("username", username)
                                            .with("password", password))
                                    .exchangeToMono(response -> {
                                        if (response.statusCode().is2xxSuccessful()) {
                                            return Mono.just(new UsernamePasswordAuthenticationToken(
                                                    username,
                                                    password,
                                                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
                                            ));
                                        } else {
                                            return Mono.error(new BadCredentialsException("Identifiants incorrects"));
                                        }
                                    });
                        })
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
                            response.setStatusCode(HttpStatus.FOUND);
                            response.getHeaders().setLocation(URI.create("/home"));
                            return response.setComplete();
                        })
                        .authenticationFailureHandler((webFilterExchange, exception) -> {
                            ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
                            response.setStatusCode(HttpStatus.FOUND);
                            response.getHeaders().setLocation(URI.create("/login?error"));
                            return response.setComplete();
                        })
                )
                .logout(logout -> logout.logoutUrl("/logout"));

        return http.build();
    }

    @Bean
    public GlobalFilter addUserHeaderFilter() {
        return (exchange, chain) ->
                ReactiveSecurityContextHolder.getContext()
                        .flatMap(securityContext -> {
                            Authentication auth = securityContext.getAuthentication();
                            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                                String currentUserName = auth.getName();
                                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                        .header("userX", currentUserName)
                                        .build();
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }
                            return chain.filter(exchange);
                        })
                        .switchIfEmpty(chain.filter(exchange));
    }
}
