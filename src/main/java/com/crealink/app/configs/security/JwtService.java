package com.crealink.app.configs.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.crealink.app.configs.AppConfigValue;
import com.crealink.app.dto.user.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {
    
    private final AppConfigValue appConfigValue;

    // Generate token with roles (authorities)
    public String generateToken(UserDto userDto) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", userDto.username());

        return Jwts.builder()
            .subject(userDto.id())
            .claims(claims)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + appConfigValue.getExpireTime()))
            .signWith(getKey())
            .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(appConfigValue.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extract external id from token
    public String extractUserExternalId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract specific claim from token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    // Validate the token (e.g., check expiration)
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
