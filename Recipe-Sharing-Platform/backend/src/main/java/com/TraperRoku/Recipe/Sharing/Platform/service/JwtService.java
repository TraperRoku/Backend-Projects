package com.TraperRoku.Recipe.Sharing.Platform.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "my_super_secure_secret_key_32_bytes_long"; // Klucz musi być spójny
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

    public String extractLogin(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getIssuer(); // Wyciąganie loginu użytkownika
    }
}