package com.loginUsingGoogleAndGithub.demo.controller;


import com.loginUsingGoogleAndGithub.demo.entity.User;
import com.loginUsingGoogleAndGithub.demo.repository.UserRepository;
import com.loginUsingGoogleAndGithub.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import com.loginUsingGoogleAndGithub.demo.entity.User;
import com.loginUsingGoogleAndGithub.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {




    @Autowired
    private AuthService authService;
    @GetMapping("/login")
    public Map<String, Object> login(OAuth2AuthenticationToken authentication) {
        return authService.login(authentication);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);  // Invalidate session & clear authentication
        }

        // Remove cookies (if any) from the client
        response.setHeader("Set-Cookie", "JSESSIONID=; HttpOnly; Path=/; Max-Age=0; SameSite=Lax");

        return "Successfully logged out.";
    }


    @Autowired
    private  UserRepository userRepository;



    @GetMapping("/user")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal OAuth2User oauthUser) {

        String email = oauthUser.getAttribute("email");
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
