package com.example.lime_education.system.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.lime_education.system.response.ApiResponseDto;
import com.example.lime_education.system.utils.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        setFilterProcessesUrl("/api/v1/users/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(),
                    LoginRequest.class);
            List<GrantedAuthority> authorities = getAuthorities(List.of("ROLE_USER"));

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword(),
                            authorities
//                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<GrantedAuthority> getAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication authentication) throws IOException {
        UserDetailsImpl userDetails = ((UserDetailsImpl) authentication.getPrincipal());

        // Jwt token 생성 access token 생성
        String token = jwtUtil.createToken(userDetails.getUserId(), userDetails.getEmail());
        String userToken = "Bearer " + userDetails.getUserToken();
        System.out.println("user Token" + userToken);

        handleLoginSuccess(response, userDetails, token, userToken);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request,
                                           HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        String result = new ObjectMapper().writeValueAsString(
                new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "login failure")
        );

        response.getOutputStream().print(result);
    }

    private void handleLoginSuccess(HttpServletResponse response, UserDetailsImpl userDetails, String token, String userToken) throws IOException {
        // UserRefreshToken userRefreshToken = getOrGenerate(userDetails.getUserId());

        // Add refresh token as a cookie
//        addRefreshTokenCookie(response, userRefreshToken);

        // cookieUtil.addRefreshTokenCookie(response, userRefreshToken);

        // Add JWT token in the Authorization header
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        response.addHeader("userToken", userToken);

        // Set response status and content type
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
    }

    
}
