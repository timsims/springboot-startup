package com.bootstrap.startup.configurations;

import com.bootstrap.startup.components.jwt.JwtEncoder;
import com.bootstrap.startup.components.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Bean
    public JwtEncoder getJwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expired}") Integer expired) {
        return new JwtUtil(secret, expired);
    }
}
