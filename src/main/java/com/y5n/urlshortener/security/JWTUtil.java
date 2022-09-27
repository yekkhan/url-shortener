package com.y5n.urlshortener.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JWTUtil {
    private final Algorithm algorithm = Algorithm.HMAC256("y5n".getBytes());

    public DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String getAccessToken(String username, String issuer, String[] roles) {

        return generateJWTToken(username, issuer, roles,
                new Date(System.currentTimeMillis() + 60 * 60 * 1000)); // 1 hour
    }

    public String getRefreshToken(String username, String issuer, String[] roles) {

        return generateJWTToken(username, issuer, roles,
                new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // 1 day
    }

    private String generateJWTToken(String username, String issuer, String[] roles, Date expiresAt) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .withArrayClaim("roles", roles)
                .sign(algorithm);
    }
}
