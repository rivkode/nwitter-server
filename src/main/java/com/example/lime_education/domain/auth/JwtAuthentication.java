package com.example.lime_education.domain.auth;

import lombok.Getter;

@Getter
public class JwtAuthentication {
    private String userToken;
    private String accessToken;

    public JwtAuthentication() {

    }

    public JwtAuthentication(String userToken, String accessToken) {
        this.userToken = userToken;
        this.accessToken = accessToken;
    }
}
