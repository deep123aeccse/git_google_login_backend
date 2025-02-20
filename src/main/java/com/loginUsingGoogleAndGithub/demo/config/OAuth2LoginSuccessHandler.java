package com.loginUsingGoogleAndGithub.demo.config;

import com.loginUsingGoogleAndGithub.demo.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private  AuthService authService;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = token.getPrincipal();

//        authService.processOAuthPostLogin(oauthUser, token.getAuthorizedClientRegistrationId());

        Cookie sessionCookie = new Cookie("SESSION", request.getSession().getId());
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(60 * 60); // 1 hour
        response.addCookie(sessionCookie);

        response.sendRedirect("https://deeploginassignment.netlify.app/dashboard");
    }
}