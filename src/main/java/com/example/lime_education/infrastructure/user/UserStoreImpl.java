package com.example.lime_education.infrastructure.user;

import org.springframework.stereotype.Component;

import com.example.lime_education.domain.user.User;
import com.example.lime_education.domain.user.UserStore;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserStoreImpl implements UserStore {
    private final UserRepository userRepository;

    @Override
    public User store(User user) {
        return userRepository.save(user);
    }
    
}
