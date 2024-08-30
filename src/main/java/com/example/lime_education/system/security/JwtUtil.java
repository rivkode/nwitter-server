package com.example.lime_education.system.security;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.lime_education.domain.auth.CustomClaims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "authorization"; // Header KEY 값
    public static final String BEARER_PREFIX = "Bearer "; // Token 식별자
    private static final long TOKEN_TIME = Duration.ofDays(5).toMillis(); // 토큰 만료시간 5 days

    @Value("${jwt.secret}") // Base 64 decode시 사용하는 Key
    private String secretKey;
    private Key key;
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    public enum TokenStatus {
        VALID,
        INVALID,
        EXPIRED
    }

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Long userId, String email) {
        Date date = new Date();

        return BEARER_PREFIX + 
                Jwts.builder()
                            .setSubject(email) // 사용자 식별
                            .claim("id", userId)
                            .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                            .setIssuedAt(date)
                            .signWith(key, signatureAlgorithm)
                            .compact();
    }

    public TokenStatus validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return TokenStatus.VALID;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            return TokenStatus.INVALID;
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
            logger.info("after expired error log");
            return TokenStatus.EXPIRED;

        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            return TokenStatus.INVALID;
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            return TokenStatus.INVALID;
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public CustomClaims getUserId(String accessToken) {
        Claims claims = getUserInfoFromToken(accessToken);

        return new CustomClaims(claims.get("token", String.class), List.of("ROLE_USER"));
    }

    public String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        } else {
            throw new IllegalArgumentException("getTokenFromRequest(): BEARER_PREFIX가 Bearer 와 일치하지 않습니다.");
        }
    }
}
