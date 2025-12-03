package com.example.authapi.controller;

import com.example.authapi.entity.User;
import com.example.authapi.repository.UserRepository;
import com.example.authapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class GoogleAuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping("/api/auth/google-success")
    public Map<String, String> googleSuccess(@AuthenticationPrincipal OAuth2User principal){
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .fullName(name)
                        .password("GOOGLE_USER_" + System.currentTimeMillis())
                        .build()
                ));
        String token = jwtService.generateToken(user);

        return Map.of(
                "token", token,
                "message", "Google login successful",
                "email", email
        );
    }


}
