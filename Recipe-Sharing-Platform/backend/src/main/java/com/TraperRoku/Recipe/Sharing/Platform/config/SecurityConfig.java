package com.TraperRoku.Recipe.Sharing.Platform.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(userAuthenticationEntryPoint))
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                        // Allow all recipe-related GET endpoints
                        .requestMatchers(HttpMethod.GET,
                                "/api/recipes",
                                "/api/recipes/uploads/images/**",
                                "/api/recipes/**",
                                "/api/recipes/find",
                                "/api/recipes/find/**",
                                "/api/recipes/difficulties",
                                "/api/recipes/tags",
                                "/api/recipes/chefs"
                        ).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/recipes/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}