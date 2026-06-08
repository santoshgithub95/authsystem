package com.example.authsystem.config;

import com.example.authsystem.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Auth APIs
                        .requestMatchers(
                                "/auth/login",
                                "/auth/refresh"
                        ).permitAll()

                        // Swagger APIs
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Admin APIs
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // User APIs Permission Based
                        .requestMatchers(HttpMethod.POST, "/users")
                        .hasAuthority("CREATE_USER")

                        .requestMatchers(HttpMethod.GET, "/users/**")
                        .hasAuthority("READ_USER")

                        .requestMatchers(HttpMethod.PUT, "/users/**")
                        .hasAuthority("UPDATE_USER")

                        .requestMatchers(HttpMethod.DELETE, "/users/**")
                        .hasAuthority("DELETE_USER")

                        // Remaining APIs
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}