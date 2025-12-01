package com.example.authapi.controller;

import com.example.authapi.dto.AuthRequest;
import com.example.authapi.dto.AuthResponse;
import com.example.authapi.dto.RegisterRequest;
import com.example.authapi.entity.User;
import com.example.authapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        if(userRepository.existsByEmail(request.email())){
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(null, "Email already exists"));
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);

        return ResponseEntity.ok(new AuthResponse("JWT_TOKEN_HERE","Registered Successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return userRepository.findByEmail(request.email())
                .filter(user -> passwordEncoder.matches(request.password(),user.getPassword()))
                .map(user -> ResponseEntity.ok(
                        new AuthResponse("JWT_TOKEN_HERE", "Login successful")))
                .orElse(ResponseEntity.status(401)
                        .body(new AuthResponse(null,"Invalid credentials")));
    }

}
