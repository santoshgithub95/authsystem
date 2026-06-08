package com.example.authsystem.security;

import com.example.authsystem.entity.Permission;
import com.example.authsystem.entity.Role;
import com.example.authsystem.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(User user) {

        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();

        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName());

            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getPermissionName());
            }
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("tenantId", user.getTenantId());
        claims.put("roles", roles);
        claims.put("permissions", permissions);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}