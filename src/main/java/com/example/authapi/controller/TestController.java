package com.example.authapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok("Hello " + userDetails.getUsername() + "! You are authenticated!");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminOnly(){
        return ResponseEntity.ok("Welcome Admin! This is protected");
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasAllRoles('ADMIN')")
    public ResponseEntity<String> listUsers(){
        return ResponseEntity.ok("Admin is viewing all users");
    }
}
