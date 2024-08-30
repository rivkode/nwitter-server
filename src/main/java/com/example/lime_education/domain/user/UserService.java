package com.example.lime_education.domain.user;

public interface UserService {
    UserInfo registerUser(UserCommand.RegisterUser command);
}
