package com.bootstrap.startup.components.encryption;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AesEncrypterTest {

    @Test
    public void testBase64Encryption() {
        var key = "base64:OUM0DBDvyBumCOybh5K7PdRTbmzXGZtp6eagbUjcX1Q=";
        key = key.substring(7);
        var keyBite = Base64.getDecoder().decode(key);

        var e = new AesEncrypter(keyBite);
        var encrypt = e.encrypt("foo", keyBite);
        assertThat(e.decrypt(encrypt)).isEqualTo("foo");
    }

    @Test
    public void testEncryption() {
        var e = new AesEncrypter("a".repeat(16));
        var encrypted = e.encrypt("foo");
        assertThat(encrypted).isNotEqualTo("foo");
        assertThat(e.decrypt(encrypted)).isEqualTo("foo");
    }

    @Test
    public void testEncryptedLengthIsFixed() {
        var e = new AesEncrypter("a".repeat(16));
        List<Integer> lengths = new ArrayList<>();
        for (var i = 0; i < 100; i++) {
            lengths.add(e.encrypt("foo").length());
        }
        assertThat(Collections.min(lengths)).isEqualTo(Collections.max(lengths));
    }

    @Test
    public void testProductionPassword() {
        var actual = "admin@admin.com123456TMG";
        var encrypted = getEncrypt().encrypt(actual);
        assertThat(getEncrypt().decrypt(encrypted)).isEqualTo(actual);
    }

    public Encrypter getEncrypt() {
        var key = "base64:OUM0DBDvyBumCOybh5K7PdRTbmzXGZtp6eagbUjcX1Q=";
        key = key.substring(7);
        var keyBite = Base64.getDecoder().decode(key);
        return new AesEncrypter(keyBite);
    }
}