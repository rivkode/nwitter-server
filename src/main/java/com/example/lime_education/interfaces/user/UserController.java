package com.example.lime_education.interfaces.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lime_education.application.user.UserFacade;
import com.example.lime_education.domain.user.UserCommand;
import com.example.lime_education.domain.user.UserInfo;
import com.example.lime_education.interfaces.user.UserDto.RegisterRequest;
import com.example.lime_education.interfaces.user.UserDto.RegisterResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserFacade userfacade;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserCommand.RegisterUser command = request.toCommand();
        UserInfo userInfo = userfacade.registerUser(command);
        RegisterResponse response = new RegisterResponse(userInfo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
