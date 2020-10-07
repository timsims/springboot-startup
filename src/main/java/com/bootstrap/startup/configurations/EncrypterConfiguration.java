package com.bootstrap.startup.configurations;

import com.bootstrap.startup.components.encryption.AesEncrypter;
import com.bootstrap.startup.components.encryption.Encrypter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class EncrypterConfiguration {

    @Bean
    public Encrypter encrypter(@Value("${app_key}") String key) {
        if (isBase64Encode(key)) {
            // 去掉 base64:
            byte[] realKey = Base64.getDecoder().decode(key.substring(7));
            return new AesEncrypter(realKey);
        }

        return new AesEncrypter(key);
    }

    protected boolean isBase64Encode(String key) {
        return key.startsWith("base64:");
    }
}
