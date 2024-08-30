package com.example.lime_education.system.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.lime_education.domain.auth.JwtAuthenticationProvider;
import com.example.lime_education.system.response.ApiResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationProvider authenticationProvider;
    // private static final AntPathRequestMatcher SIGNUP_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/users/signup", HttpMethod.POST.toString());

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = jwtAuthenticationProvider;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {"/api/v1/users/signup", "/api/v1/users/login"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Header에서 jwt 토큰 받아오기
        String tokenValue = jwtUtil.getTokenFromRequest(request);

        if (StringUtils.hasText(tokenValue)) {
            // 토큰 검증
            // if (isNotValidate(request, response, tokenValue)) return;

            // 토큰에서 사용자 정보 가져오기
            Claims info = getClaims(response, tokenValue);
            if (info == null) return;

            // 사용자 정보 인증 객체에 담기
            if (userInfoInAuthentication(tokenValue)) return;
        }

        filterChain.doFilter(request, response);
    }

    private Claims getClaims(HttpServletResponse response, String tokenValue) throws IOException {
        Claims info;

        try {
            info = jwtUtil.getUserInfoFromToken(tokenValue);
        } catch (Exception e) {
            // JWT 검증에 실패한 경우 처리
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            String result = new ObjectMapper().writeValueAsString(
                    new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "INVALID_TOKEN")
            );

            response.getOutputStream().print(result);
            return null;
        }
        return info;
    }

    private boolean userInfoInAuthentication(String tokenValue) {
        try {
            setAuthentication(tokenValue);
        } catch (Exception e) {
            // 인증 처리에 실패한 경우 처리
            log.error(e.getMessage());
            return true;
        }
        return false;
    }

    // 인증 처리
    public void setAuthentication(String tokenValue) {
        Authentication authentication = authenticationProvider.authenticate(tokenValue);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
}
