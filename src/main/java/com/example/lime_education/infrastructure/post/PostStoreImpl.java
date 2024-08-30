package com.example.lime_education.infrastructure.post;

import org.springframework.stereotype.Component;

import com.example.lime_education.domain.post.Post;
import com.example.lime_education.domain.post.PostStore;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostStoreImpl implements PostStore {
    private final PostRepository postRepository;

    @Override
    public Post store(Post post) {
        return postRepository.save(post);
    }


    
}
