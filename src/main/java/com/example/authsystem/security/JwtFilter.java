package com.example.authsystem.security;

import com.example.authsystem.entity.User;
import com.example.authsystem.repository.UserRepository;
import com.example.authsystem.service.RevokedTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RevokedTokenService revokedTokenService;

    public JwtFilter(JwtUtil jwtUtil,
                     UserRepository userRepository,
                     RevokedTokenService revokedTokenService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.revokedTokenService = revokedTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            if (jwtUtil.validateToken(token)
                    && !revokedTokenService.isTokenRevoked(token)) {
                username = jwtUtil.extractUsername(token);
            }
        }

        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null) {

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                user.getRoles().forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

                    role.getPermissions().forEach(permission -> {
                        authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
                    });
                });

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}