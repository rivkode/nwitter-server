package com.example.lime_education.domain.post;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.lime_education.domain.post.PostCommand.RegisterPost;
import com.example.lime_education.domain.post.PostCommand.RetrievePost;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostStore postStore;
    private final PostReader postReader;

    @Override
    public PostInfo registerPost(RegisterPost command) {
        Post initPost = command.toEntity();
        Post post = postStore.store(initPost);

        return new PostInfo(post);
    }

    @Override
    public PostInfo retrievePost(RetrievePost command) {
        String postToken = command.getPostToken();
        Post post = postReader.read(postToken);

        return new PostInfo(post);
    }

    @Override
    public List<PostInfo> retrievePostList() {
        List<Post> postInfoList = postReader.readList();
        List<PostInfo> list = postInfoList.stream().map(PostInfo::new).toList();

        return list;
    }
    
}
