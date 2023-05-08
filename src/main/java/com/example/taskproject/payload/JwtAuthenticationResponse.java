package com.example.taskproject.payload;

public class JwtAuthenticationResponse {
    private String token;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
