package com.project.expenseTracker.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    // Secret key (use something secure in real-world apps!)
    private static final String SECRET_KEY = "mysupersecretkeyformyexpenseapp123456"; // Should be 256-bit for HS256

    // Token validity: 24 hours
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 1. Generate token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // username/email of the user
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. Validate token
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
