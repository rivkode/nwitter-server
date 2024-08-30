package com.example.lime_education.application.user;

import org.springframework.stereotype.Service;

import com.example.lime_education.domain.user.UserInfo;
import com.example.lime_education.domain.user.UserService;
import com.example.lime_education.domain.user.UserCommand.RegisterUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserInfo registerUser(RegisterUser command) {
        UserInfo userInfo = userService.registerUser(command);
        return userInfo;
    }
    
}
