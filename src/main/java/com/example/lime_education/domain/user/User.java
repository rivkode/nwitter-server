package com.example.lime_education.domain.user;

import com.example.lime_education.common.util.TokenGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class User {
    private static final String USER_PREFIX = "usr_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userToken;
    private String username;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    public enum Role {
        ROLE_ADMIN("admin"), ROLE_USER("user");
        private String description;

        Role(String description) {
            this.description = description;
        }
    }

    @Builder
    public User(String username, String email, String password, String role) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("empty username");
        if (email == null || email.length() == 0) throw new IllegalArgumentException("empty email");
        if (password == null || password.length() == 0) throw new IllegalArgumentException("empty password");

        this.userToken = TokenGenerator.randomCharacterWithPrefix(USER_PREFIX);
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = Role.ROLE_USER;
    }

    public User() {

    }
}
