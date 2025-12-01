package com.example.authapi.dto;

public record AuthResponse (
        String token,
        String message
){}
