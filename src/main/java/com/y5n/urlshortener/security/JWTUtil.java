package com.y5n.urlshortener.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTUtil {
    private final JWTVerifier verifier;

    public JWTUtil() {
        Algorithm algorithm = Algorithm.HMAC256("y5n".getBytes());
        verifier = JWT.require(algorithm).build();
    }

    public DecodedJWT getDecodedJWT(String token) {

        return verifier.verify(token);
    }
}
