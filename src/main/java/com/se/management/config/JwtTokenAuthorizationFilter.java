package com.se.management.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.se.management.config.jwt.JwtTokenProvider;
import com.se.management.model.request.LoginRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


public class JwtTokenAuthorizationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public JwtTokenAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProperties,
                                       JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        objectMapper = new ObjectMapper();
        this.authManager = authenticationManager;
        setLoginPath(tokenProperties);

    }

    private void setLoginPath(JwtTokenProvider tokenProperties) {
        setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(tokenProperties.getUri(), "POST"));
    }

    //called whenever the API gets a request to the login path
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {

            // 1. Get credentials from request
            LoginRequest credential = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);

            // 2. Create auth object (contains credentials) which will be used by auth manager
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credential.getEmail(), credential.getPassword(), Collections.emptyList());

            // 3. Authentication manager authenticate the user
            return authManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // add the Authorization header with the JWT token data to the response.
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) {

        String token = tokenProvider.createToken(auth);
        response.addHeader(tokenProvider.getHeader(), tokenProvider.getPrefix() + token);
    }
}
