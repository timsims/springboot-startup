package com.bootstrap.startup.components.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtEncoder {
    String generateToken(UserDetails userDetails);

    String extractUsername(String token);
}
