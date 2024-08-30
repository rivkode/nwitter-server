package com.example.lime_education.domain.post;

import java.util.List;

public interface PostReader {
    Post read(String postToken);
    List<Post> readList();
}
