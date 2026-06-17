package com.example.collab.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expirationMs) {

        byte[] keyBytes = Base64.getDecoder().decode(secret.trim());

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        this.expirationMs = expirationMs;

    }

    public String generateToken(String username, List<String> roles) {

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();

    }

    public String extractUsername(String token) {

        return extractClaims(token).getSubject();

    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {

        return extractClaims(token).get("roles", List.class);

    }

    public boolean isTokenValid(String token) {

        try {

            extractClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;

        }
    }

    private Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    
}
