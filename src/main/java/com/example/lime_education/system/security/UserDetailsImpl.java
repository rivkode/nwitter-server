package com.example.lime_education.system.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.lime_education.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final User user;

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Long getUserId() {
        return user.getId();
    }

    public String getUserToken() {
        return user.getUserToken();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String authority = "ROLE_USER";
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
