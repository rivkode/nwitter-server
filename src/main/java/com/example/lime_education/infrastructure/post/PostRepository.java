package com.example.lime_education.infrastructure.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lime_education.domain.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByPostToken(String postToken);
}
