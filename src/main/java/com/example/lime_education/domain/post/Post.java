package com.example.lime_education.domain.post;

import com.example.lime_education.common.util.TokenGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Post {

    private static final String USER_PREFIX = "pst_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String postToken;
    private String title;
    private String content;

    @Builder
    public Post(String title, String content) {
        this.postToken = TokenGenerator.randomCharacterWithPrefix(USER_PREFIX);
        this.title = title;
        this.content = content;
    }

    public Post() {
        
    }
}
