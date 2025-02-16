package com.TraperRoku.config;

import com.TraperRoku.dto.UserDto;
import com.TraperRoku.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    private static final String secretKey = "my_super_secure_secret_key_32_bytes_long";

    private final UserService userService;
    private static final String ROLE_CLAIM = "role";

    private Algorithm algorithm;

    @PostConstruct
    protected void init() {
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String createToken(String login) {

        Date now = new Date();

        UserDto user = userService.findByLogin(login);
        String role = user.getRole();

        return JWT.create()
                .withIssuer(login)  // Ustawienie loginu użytkownika jako issuer
                .withClaim(ROLE_CLAIM, role)
                .withIssuedAt(now)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 dni
                .sign(algorithm);
    }
    public Authentication validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String login = decodedJWT.getIssuer();
        String role = decodedJWT.getClaim(ROLE_CLAIM).asString();



        UserDto user = userService.findByLogin(decodedJWT.getIssuer());

        if (role == null) {
            role = "USER";
        }

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }
}
