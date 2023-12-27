package com.example.rating_microservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity httpSec) throws Exception {
        // Authorize all requests
        httpSec.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                // Configure OAuth2 resource server with JWT
                .oauth2ResourceServer(server -> server.jwt(Customizer.withDefaults()));

        return httpSec.build();
    }
}
