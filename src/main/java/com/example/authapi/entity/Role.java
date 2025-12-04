package com.example.authapi.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Set.of("ROLE_USER")),
    ADMIN(Set.of("ROLE_ADMIN", "ROLE_USER"));

    private final Set<String> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }



}
