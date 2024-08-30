package com.example.lime_education.domain.post;

import java.util.List;

public interface PostService {
    PostInfo registerPost(PostCommand.RegisterPost command);
    PostInfo retrievePost(PostCommand.RetrievePost command);
    List<PostInfo> retrievePostList();
    
}
