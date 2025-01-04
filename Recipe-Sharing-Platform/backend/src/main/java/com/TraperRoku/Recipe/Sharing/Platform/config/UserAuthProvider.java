package com.TraperRoku.Recipe.Sharing.Platform.config;

import com.TraperRoku.Recipe.Sharing.Platform.dto.ChefDto;
import com.TraperRoku.Recipe.Sharing.Platform.service.ChefService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    private static final String secretKey = "my_super_secure_secret_key_32_bytes_long";

    private final ChefService chefService;

    private Algorithm algorithm;

    @PostConstruct
    protected void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String createToken(String login) {
        Date now = new Date();


        return JWT.create()
                .withIssuer(login)  // Ustawienie loginu użytkownika jako issuer
                .withIssuedAt(now)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 dni
                .sign(algorithm);
    }
    public Authentication validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        ChefDto user = chefService.findByLogin(decodedJWT.getIssuer());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
