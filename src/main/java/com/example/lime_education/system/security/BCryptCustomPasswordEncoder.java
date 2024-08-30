package com.example.lime_education.system.security;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lime_education.system.common.CustomPasswordEncoder;

public class BCryptCustomPasswordEncoder implements CustomPasswordEncoder {
    private final PasswordEncoder passwordEncoder;

    public BCryptCustomPasswordEncoder() {
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean noneMatchesPassword(String rawPassword, String encodedPassword) {
        return !matchesPassword(rawPassword, encodedPassword);
    }
}
