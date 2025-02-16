package com.loginUsingGoogleAndGithub.demo.controller;


import com.loginUsingGoogleAndGithub.demo.service.AuthService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public Map<String, String> login(OAuth2AuthenticationToken authentication) {
        return authService.login(authentication);
    }

    @PostMapping("/logout")
    public Map<String, String> logout() {
        return authService.logout();
    }
}
