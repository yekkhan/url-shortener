package com.y5n.urlshortener.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getServletPath().contains("/api/v1")) {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (Exception exception) {
                    log.error("Error authorizing user: " + exception.getMessage());

                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);

                    Map<String, String> errorMessageBody = buildErrorMessageBody(exception.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), errorMessageBody);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        JWTUtil jwtUtil = new JWTUtil();
        DecodedJWT decodedJWT = jwtUtil.getDecodedJWT(token);

        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private Map<String, String> buildErrorMessageBody(String errorMessage) {
        Map<String, String> error = new HashMap<>();
        long date = new Date().getTime();

        error.put("timestamp", Long.toString(date));
        error.put("status", String.valueOf(FORBIDDEN.value()));
        error.put("error", FORBIDDEN.getReasonPhrase());
        error.put("message", errorMessage);

        return error;
    }
}
