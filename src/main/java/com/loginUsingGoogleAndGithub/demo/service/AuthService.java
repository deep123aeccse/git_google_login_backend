package com.loginUsingGoogleAndGithub.demo.service;

import com.loginUsingGoogleAndGithub.demo.entity.User;
import com.loginUsingGoogleAndGithub.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class AuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> login(OAuth2AuthenticationToken authentication) {
        log.info(authentication.toString());
        OAuth2User oauthUser = authentication.getPrincipal();

        // Extract user details
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String provider = authentication.getAuthorizedClientRegistrationId();
        String providerId = oauthUser.getAttribute("id");

        // Check if user exists, if not create a new one
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setProvider(provider);
            newUser.setProviderId(providerId);
            return userRepository.save(newUser);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("user", user);

        return response;
    }

    public String logout() {
      return "Null";

    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return null;
    }
}
