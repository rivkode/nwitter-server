package com.example.lime_education.infrastructure.post;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.lime_education.domain.post.Post;
import com.example.lime_education.domain.post.PostReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {
    private final PostRepository postRepository;

    @Override
    public Post read(String postToken) {
        return postRepository.findByPostToken(postToken);

    }

    @Override
    public List<Post> readList() {
        return postRepository.findAll();
    }
    
    
}
