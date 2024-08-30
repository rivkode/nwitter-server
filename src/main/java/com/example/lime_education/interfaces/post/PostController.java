package com.example.lime_education.interfaces.post;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lime_education.application.post.PostFacade;
import com.example.lime_education.domain.post.PostCommand;
import com.example.lime_education.domain.post.PostInfo;
import com.example.lime_education.domain.post.PostCommand.RetrievePost;
import com.example.lime_education.interfaces.post.PostDto.RegisterRequest;
import com.example.lime_education.interfaces.post.PostDto.RegisterResponse;
import com.example.lime_education.interfaces.post.PostDto.RetrieveResponse;
import com.example.lime_education.interfaces.post.PostDto.RetrieveResponseList;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostFacade postFacade;

    @PostMapping
    public ResponseEntity<RegisterResponse> registerPost(@Valid @RequestBody RegisterRequest request) {
        PostCommand.RegisterPost command = request.toCommand();
        PostInfo postInfo = postFacade.registerPost(command);
        RegisterResponse response = new RegisterResponse(postInfo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{postToken}")
    public ResponseEntity<RetrieveResponse> retrievePost(@PathVariable("postToken") String postToken) {
        PostCommand.RetrievePost command = RetrievePost.builder().postToken(postToken).build();
        PostInfo postInfo = postFacade.retrievePost(command);
        RetrieveResponse response = new RetrieveResponse(postInfo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<RetrieveResponseList> retrievePostList() {
        List<PostInfo> postInfoList = postFacade.retrievePostList();
        RetrieveResponseList response = new RetrieveResponseList(postInfoList);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    
}
