package com.example.lime_education.domain.user;

import lombok.Builder;
import lombok.Getter;

public class UserCommand {
    @Getter
    @Builder
    public static class RegisterUser {
        private final String username;
        private final String email;
        private final String password;
        
        public User toEntity(String encryptPassword) {
            return User.builder()
                    .email(email)
                    .password(encryptPassword)
                    .username(username)
                    .build();
        }
    }
}
