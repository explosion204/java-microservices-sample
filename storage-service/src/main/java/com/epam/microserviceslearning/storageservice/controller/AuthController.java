package com.epam.microserviceslearning.storageservice.controller;

import com.epam.microserviceslearning.storageservice.security.AuthService;
import com.epam.microserviceslearning.storageservice.security.model.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storages/auth")
public class AuthController {
    private final AuthService authService;

    @Value("${service.auth.auth-uri}")
    private String authUri;

    @GetMapping
    public ModelAndView redirectToAuthProvider() {
        return new ModelAndView("redirect:" + authUri);
    }

    @GetMapping("/code")
    public AccessTokenResponse sendAuthorizationCode(@RequestParam("code") String authorizationCode) {
        return authService.getAccessToken(authorizationCode);
    }
}
