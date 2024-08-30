package com.example.lime_education.domain.post;

import lombok.Getter;

@Getter
public class PostInfo {
    private final String postToken;
    private final String title;
    private final String content;

    public PostInfo(Post post) {
        this.postToken = post.getPostToken();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

}
