package com.example.lime_education.interfaces.post;

import java.util.List;
import java.util.stream.Collectors;

import com.example.lime_education.domain.post.PostCommand;
import com.example.lime_education.domain.post.PostInfo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostDto {
    @Getter
    @NoArgsConstructor
    public static class RegisterRequest {
        // @NotNull(message = "title은 필수 입력 값입니다.")
        private String title;

        @NotNull(message = "content는 필수 입력 값입니다.")
        private String content;
        
        public PostCommand.RegisterPost toCommand() {
            return PostCommand.RegisterPost.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    public static class RegisterResponse {
        private String postToken;

        public RegisterResponse(PostInfo postInfo) {
            this.postToken = postInfo.getPostToken();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RetrieveRequest {
        @NotNull
        private String postToken;

        public PostCommand.RetrievePost toCommand() {
            return PostCommand.RetrievePost.builder()
                    .postToken(postToken)
                    .build();
        }
    }

    

    @Getter
    public static class RetrieveResponse {
        private String title;
        private String content;

        public RetrieveResponse(PostInfo postInfo) {
            this.title = postInfo.getTitle();
            this.content = postInfo.getContent();
        }
    }

    @Getter
    public static class RetrieveResponseList {
        private List<RetrieveResponse> postList;
        
        public RetrieveResponseList(List<PostInfo> postInfoList) {
            this.postList = postInfoList.stream()
                                            .map(RetrieveResponse::new)
                                            .collect(Collectors.toList());
        }
    }

}
