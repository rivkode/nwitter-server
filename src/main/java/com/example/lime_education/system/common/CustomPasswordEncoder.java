package com.example.lime_education.system.common;

public interface CustomPasswordEncoder {
    String encodePassword(String rawPassword);

    boolean matchesPassword(String rawPassword, String encodedPassword);

    boolean noneMatchesPassword(String rawPassword, String encodedPassword);
}
