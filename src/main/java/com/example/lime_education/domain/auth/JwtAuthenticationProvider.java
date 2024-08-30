package com.example.lime_education.domain.auth;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.lime_education.system.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {
    private final JwtUtil jwtUtil;

    public Authentication authenticate(String accessToken) {
        CustomClaims claims = jwtUtil.getUserId(accessToken);
        JwtAuthentication authentication = new JwtAuthentication(claims.getUserToken(), accessToken);
        List<GrantedAuthority> authorities = getAuthorities(claims.getAuthorities());
        return UsernamePasswordAuthenticationToken.authenticated(authentication, accessToken, authorities);
    }

    private List<GrantedAuthority> getAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }
}
