package com.Filip.Expense.Tracker.API.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import java.util.Date;

@Component
public class JwtUtil {

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.JWT_SECRET)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT was expiried or is incorrect",e.fillInStackTrace());
        }
    }

}
