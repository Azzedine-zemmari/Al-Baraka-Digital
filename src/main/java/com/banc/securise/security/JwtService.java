package com.banc.securise.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private String SECRET_KEY;
    public JwtService(Dotenv dotenv){
        SECRET_KEY = dotenv.get("JWT_SECRET");
    }
    public String generateToken(UserDetails userDetails , boolean rememberMe){
        Long expiration;
        if(rememberMe){
            expiration = 1000L * 60 * 60 * 24 * 7; // 7 days
        }else{
            expiration = 1000L * 60 * 60; // 1 hour 
        }
        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(()-> new RuntimeException("role not found"));

            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .claim("role",role)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }


    public String extractRole(String token){
        return extractAllClaims(token).get("role",String.class);
    }
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // helper
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
