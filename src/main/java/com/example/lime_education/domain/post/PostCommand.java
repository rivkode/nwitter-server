package com.example.lime_education.domain.post;

import lombok.Builder;
import lombok.Getter;

public class PostCommand {
    @Getter
    @Builder
    public static class RegisterPost {
        private final String title;
        private final String content;

        public Post toEntity() {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class RetrievePost {
        private final String postToken;
    }

}
