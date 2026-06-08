package com.example.authsystem.service;

import com.example.authsystem.entity.RevokedToken;
import com.example.authsystem.repository.RevokedTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RevokedTokenService {

    private final RevokedTokenRepository revokedTokenRepository;

    public RevokedTokenService(RevokedTokenRepository revokedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    public void revokeToken(String token) {

        RevokedToken revokedToken = new RevokedToken();
        revokedToken.setToken(token);
        revokedToken.setRevokedAt(Instant.now());

        revokedTokenRepository.save(revokedToken);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }
}