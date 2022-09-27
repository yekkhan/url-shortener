package com.y5n.urlshortener.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
//@PropertySource("classpath:application-dev.properties")
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationFailureHandler(new CustomAuthenticationFilterFailureHandler());
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("username is: {}", username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        log.info("Successfully authenticated user: {}", request.getParameter("username"));
        User user = (User) authResult.getPrincipal();
        List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String[] rolesArr = new String[roles.size()];
        roles.toArray(rolesArr);

        JWTUtil jwtUtil = new JWTUtil();
        String accessToken = jwtUtil.getAccessToken(user.getUsername(), request.getRequestURL().toString(), rolesArr);
        String refreshToken = jwtUtil.getRefreshToken(user.getUsername(), request.getRequestURL().toString(), rolesArr);

        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
