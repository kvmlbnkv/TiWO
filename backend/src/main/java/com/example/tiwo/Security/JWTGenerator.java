package com.example.tiwo.Security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTGenerator {

    private final String key;

    private final String expirationTime;

    public JWTGenerator(@Value("${jwt.token.expiration}") String expirationTime, @Value("${jwt.token.key}") String key){
        this.expirationTime = expirationTime;
        this.key = key;
    }

    public String generate(Authentication authentication){
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(expirationTime))))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)), SignatureAlgorithm.HS256)
                .compact();
    }
}
