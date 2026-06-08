package com.example.authsystem.controller;

import com.example.authsystem.dto.AuthResponse;
import com.example.authsystem.dto.LoginRequest;
import com.example.authsystem.dto.RefreshTokenRequest;
import com.example.authsystem.entity.RefreshToken;
import com.example.authsystem.entity.User;
import com.example.authsystem.repository.UserRepository;
import com.example.authsystem.security.JwtUtil;
import com.example.authsystem.service.RefreshTokenService;
import com.example.authsystem.service.RevokedTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final RevokedTokenService revokedTokenService;

    public AuthController(UserRepository userRepository,
                          JwtUtil jwtUtil,
                          RefreshTokenService refreshTokenService,
                          RevokedTokenService revokedTokenService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.revokedTokenService = revokedTokenService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = jwtUtil.generateToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken(), "Bearer");
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        String newAccessToken = jwtUtil.generateToken(user);

        return new AuthResponse(newAccessToken, refreshToken.getToken(), "Bearer");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        String token = authHeader.substring(7);

        revokedTokenService.revokeToken(token);

        return ResponseEntity.ok("Logout successful. Token revoked.");
    }
}