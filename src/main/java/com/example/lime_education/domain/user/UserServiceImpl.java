package com.example.lime_education.domain.user;

import org.springframework.stereotype.Component;

import com.example.lime_education.domain.user.UserCommand.RegisterUser;
import com.example.lime_education.system.common.CustomPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStore userStore;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    public UserInfo registerUser(RegisterUser command) {
        String encryptPassword = passwordEncoder.encodePassword(command.getPassword());
        User initUser = command.toEntity(encryptPassword);
        User user = userStore.store(initUser);

        return new UserInfo(user);
    }
    
}
