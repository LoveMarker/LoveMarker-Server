package com.lovemarker.domain.auth.service;

import com.lovemarker.global.constant.TokenStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long ACCESS_TOKEN_EXPIRY_TIME = 1000L * 60 * 60 * 2; // 2시간
    private static final long REFRESH_TOKEN_EXPIRY_TIME = 1000L * 60 * 60 * 24 * 14; // 2주
    private static final String CLAIM_NAME = "userId";
    private final TokenCacheService tokenCacheService;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder()
            .encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Access Token 발급
    public String issuedAccessToken(Long userId) {
        return issuedToken("access_token", ACCESS_TOKEN_EXPIRY_TIME, userId.toString());
    }

    // Refresh Token 발급
    public String issuedRefreshToken(Long userId) {
        String refreshToken = issuedToken("refresh_token", REFRESH_TOKEN_EXPIRY_TIME, userId.toString());
        tokenCacheService.setValues(String.valueOf(userId), refreshToken, REFRESH_TOKEN_EXPIRY_TIME);
        return refreshToken;
    }

    // JWT 토큰 발급
    public String issuedToken(String tokenName, long expiryTime, String userId) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
            .setSubject(tokenName)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expiryTime));

        claims.put(CLAIM_NAME, userId);

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .signWith(getSigningKey())
            .compact();
    }

    private Key getSigningKey() {
        final byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 검증
    public long verifyToken(String token) {
        try {
            final Claims claims = getBody(token);
            return TokenStatus.TOKEN_VALID;
        } catch (RuntimeException e) {
            if (e instanceof ExpiredJwtException) {
                return TokenStatus.TOKEN_EXPIRED;
            }
            return TokenStatus.TOKEN_INVALID;
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // JWT 토큰 내용 확인
    public String getJwtContents(String token) {
        final Claims claims = getBody(token);
        return (String) claims.get(CLAIM_NAME);
    }
}

