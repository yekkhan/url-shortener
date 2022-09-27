package com.y5n.urlshortener.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.y5n.urlshortener.entity.Role;
import com.y5n.urlshortener.entity.User;
import com.y5n.urlshortener.security.JWTUtil;
import com.y5n.urlshortener.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class TokenRefreshController {

    private final UserService userService;

    @GetMapping("/refresh")
    public HttpServletResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                JWTUtil jwtUtil = new JWTUtil();
                DecodedJWT decodedJWT = jwtUtil.getDecodedJWT(refreshToken);

                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                List<String> roles = user.getRoles().stream().map(Role::getName).toList();
                String[] rolesArr = new String[roles.size()];
                roles.toArray(rolesArr);

                String accessToken = jwtUtil.getAccessToken(username, request.getRequestURL().toString(), rolesArr);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error refreshing token: " + exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }

        return response;
    }
}
