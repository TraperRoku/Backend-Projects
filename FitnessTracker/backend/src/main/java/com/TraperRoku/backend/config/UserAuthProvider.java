package com.TraperRoku.backend.config;

import com.TraperRoku.backend.Dto.UserDto;
import com.TraperRoku.backend.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    private static final String secretKey = "mySecretKey123";

    private final UserService userService;

    private Algorithm algorithm;

    @PostConstruct
    protected void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String createToken(String login) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 15_600_00);  // Czas ważności

        return JWT.create()
                .withIssuer(login)  // Ustawienie loginu użytkownika jako issuer
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        UserDto user = userService.findByLogin(decodedJWT.getIssuer());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
