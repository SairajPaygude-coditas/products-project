package com.example.products.service;

import com.example.products.entity.RefreshToken;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String username);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);

    RefreshToken createRefreshToken(String username);

    boolean isTokenExpired(String token);

    RefreshToken verifyRefreshTokenExpiration(RefreshToken token);
}
