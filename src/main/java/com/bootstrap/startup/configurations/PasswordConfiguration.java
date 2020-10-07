package com.bootstrap.startup.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfiguration {

    @Bean
    public PasswordEncoder bcryptPasswordEncoder(@Value("${security.password-strength}") Integer strength) {
        return new BCryptPasswordEncoder(strength);
    }
}
