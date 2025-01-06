package com.TraperRoku.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mySecretKey123";  // Ensure it's the same key as used in validation

    public String extractLogin(String token) {

        return getClaims(token).getIssuer();
    }

     private Claims getClaims(String token) {
        // Use the raw secret key for parsing

            return Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

    }
}

