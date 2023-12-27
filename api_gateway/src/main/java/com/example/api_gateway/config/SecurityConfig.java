package com.example.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // This method is required to configure the security filter chain.
    // It is called by the Spring Boot framework.
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSec) {

        httpSec
                .authorizeExchange(exchange -> exchange
                        .anyExchange()
                        .authenticated())
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(server -> server
                        .jwt(Customizer.withDefaults()));

        return httpSec.build();
    }

}
