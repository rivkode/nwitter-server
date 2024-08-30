package com.example.lime_education.interfaces.user;

import com.example.lime_education.domain.user.UserCommand;
import com.example.lime_education.domain.user.UserInfo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {
    @Getter
    @NoArgsConstructor
    public static class RegisterRequest {
        @NotNull(message = "email은 필수 입력 값입니다.")
        private String email;

        @NotNull(message = "username은 필수 입력 값입니다.")
        private String username;

        @NotNull(message = "password는 필수 입력 값입니다.")
        private String password;

        public UserCommand.RegisterUser toCommand() {
            return UserCommand.RegisterUser.builder()
                    .email(email)
                    .username(username)
                    .password(password)
                    .build();
        }
    }

    @Getter
    public static class RegisterResponse {
        private String userToken;

        public RegisterResponse(UserInfo userInfo) {
            this.userToken = userInfo.getUserToken();
        }
    }
}
