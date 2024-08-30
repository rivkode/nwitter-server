package com.example.lime_education.domain.auth;

import java.util.List;

import lombok.Getter;

@Getter
public class CustomClaims {
    private String userToken;
    private List<String> authorities;

    public CustomClaims() {
        
    }

    public CustomClaims(String userToken, List<String> authorities) {
        this.userToken = userToken;
        this.authorities = authorities;
    }

    public static CustomClaims of(String userToken, List<String> authorities) {
        return new CustomClaims(userToken, authorities);
    }
}
