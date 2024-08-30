package com.example.lime_education.domain.user;

import lombok.Getter;

@Getter
public class UserInfo {
    private final String userToken;
    private final String username;
    private final String email;

    public UserInfo(User user) {
        this.userToken = user.getUserToken();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
    
}
