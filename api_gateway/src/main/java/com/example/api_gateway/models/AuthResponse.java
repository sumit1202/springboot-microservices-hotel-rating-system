package com.example.api_gateway.models;

import java.util.Collection;

import lombok.Data;

@Data
public class AuthResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private long expiresAt;
    private Collection<String> authorities;
}
