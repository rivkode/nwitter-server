package com.example.lime_education.application.post;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lime_education.domain.post.PostInfo;
import com.example.lime_education.domain.post.PostService;
import com.example.lime_education.domain.post.PostCommand.RegisterPost;
import com.example.lime_education.domain.post.PostCommand.RetrievePost;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFacade {
    private final PostService postService;

    public PostInfo registerPost(RegisterPost command) {
        PostInfo postInfo = postService.registerPost(command);
        return postInfo;
    }
    
    public PostInfo retrievePost(RetrievePost command) {
        PostInfo postInfo = postService.retrievePost(command);
        return postInfo;
    }
    
    public List<PostInfo> retrievePostList() {
        List<PostInfo> postInfoList = postService.retrievePostList();
        return postInfoList;
    }
}
