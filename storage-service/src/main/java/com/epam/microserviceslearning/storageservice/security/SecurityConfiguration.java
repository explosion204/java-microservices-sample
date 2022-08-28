package com.epam.microserviceslearning.storageservice.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String AUTH_ENDPOINTS = "/storages/auth/**";

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers(AUTH_ENDPOINTS);
    }

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) {
         return http
                 .addFilterBefore(new JwtFilter(authManager), BasicAuthenticationFilter.class)
                 .csrf()
                    .disable()
                 .sessionManagement()
                    .sessionCreationPolicy(STATELESS)
                 .and()
                 .authorizeRequests()
                    .anyRequest().authenticated()
                 .and()
                 .build();
    }

    @Bean
    @SneakyThrows
    public AuthenticationManager authManager(HttpSecurity http, GithubAuthenticationProvider githubAuthProvider) {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(githubAuthProvider)
                .build();
    }
}
