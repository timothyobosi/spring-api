package com.example.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        //Public APIs
                        .requestMatchers("/api/auth/**").permitAll()

                        //Swagger UI & OpenAPI (Spring Boot 3 + springdoc)
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        //fallback for new default swagger path
                        .requestMatchers("/swagger-ui/index.html").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(form->form.disable())
                .httpBasic(basic->basic.disable());

        return http.build();
    }
}
