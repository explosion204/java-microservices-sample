package com.epam.microserviceslearning.storageservice.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nullable;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String accessToken = extractAccessToken(request);
        final Authentication authentication = new GithubAuthentication(accessToken);
        authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(SC_UNAUTHORIZED);
        }
    }

    @Nullable
    private String extractAccessToken(HttpServletRequest request) {
        final String authorizationHeaderValue = request.getHeader(AUTHORIZATION_HEADER);
        return StringUtils.substringAfter(authorizationHeaderValue, BEARER);
    }
}
