package com.bootstrap.startup.components.encryption;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AesEncrypter implements Encrypter {
    /**
     * aes 加密秘钥
     */
    protected byte[] key;

    protected SecretKeySpec secretKeySpec;

    /**
     * 加密算法
     */
    protected Cipher cipher;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected final Base64.Encoder encoder = Base64.getEncoder();

    protected final Base64.Decoder decoder = Base64.getDecoder();

    public AesEncrypter(String key) {
        this(key.getBytes());
    }

    public AesEncrypter(byte[] key) {
        if (supported(key)) {
            this.key = key;
            init();
        } else {
            throw new RuntimeException("The only supported ciphers are AES-128-CBC and AES-256-CBC with the correct key lengths.");
        }
    }

    protected void init() {

        secretKeySpec = new SecretKeySpec(key, "AES");
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Determine if the given key and cipher combination is valid.
     *
     * @param key
     * @return boolean
     */
    public static boolean supported(byte[] key) {
        return key.length == 16 || key.length == 32;
    }

    @Override
    public String encrypt(String value) {
        return encrypt(value, key);
    }

    @Override
    public String decrypt(String payload) {
        return decrypt(payload, key);
    }

    public String encrypt(String plaintext, byte[] keyValue) {
        String serializedPlaintext = "s:" + plaintext.getBytes().length + ":\"" + plaintext + "\";";

        byte[] encrypted = null;
        try {

            byte[] plaintextBytes = serializedPlaintext.getBytes("UTF-8");

            // 可以自定义 iv
//            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] iv = cipher.getIV();

            byte[] encVal = cipher.doFinal(plaintextBytes);
            String encryptedData = encoder.encodeToString(encVal);
            var mac = calcMac(encryptedData, iv);

            EncryptObject aesData = new EncryptObject(
                    encoder.encodeToString(iv),
                    encryptedData,
                    mac);

            String aesDataJson = (new ObjectMapper()).writeValueAsString(aesData);
            encrypted = aesDataJson.getBytes("UTF-8");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * 生成 HmacSHA256 校验
     *
     * @param encryptedData
     * @param iv
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public String calcMac(String encryptedData, byte[] iv) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeySpec macKey = new SecretKeySpec(key, "HmacSHA256");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(macKey);
        hmacSha256.update(encoder.encode(iv));
        byte[] calcMac = hmacSha256.doFinal(encryptedData.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encodeHex(calcMac));
    }

    public String decrypt(String input, byte[] key) {
        // 1. 先对 input 进行 Base64 解编码
        // 2. 对 json decode 获取到加密对象, 获取到 iv (加密向量), value (加密数据), mac (校验)

        byte[] decodedInput = decoder.decode(input);

        byte[] output = null;
        try {

            EncryptObject encryptObject = objectMapper.readValue(decodedInput, EncryptObject.class);

            if (!verifyMac(encryptObject)) {
                return "invalid";
            }
            byte[] iv = decoder.decode(encryptObject.iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
            output = cipher.doFinal(Base64.getDecoder().decode(encryptObject.value));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        assert output != null;

        int firstQuoteIndex = 0;
        while (output[firstQuoteIndex] != (byte) '"') {
            firstQuoteIndex++;
        }
        return new String(Arrays.copyOfRange(output, firstQuoteIndex + 1, output.length - 2));
    }

    /**
     * 校验数据
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws DecoderException
     */
    public boolean verifyMac(EncryptObject encryptObject) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, DecoderException {
        SecretKeySpec macKey = new SecretKeySpec(key, "HmacSHA256");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(macKey);
        hmacSha256.update(encryptObject.iv.getBytes(StandardCharsets.UTF_8));
        byte[] calcMac = hmacSha256.doFinal(encryptObject.value.getBytes(StandardCharsets.UTF_8));
        byte[] mac = Hex.decodeHex(encryptObject.mac.toCharArray());
        return Arrays.equals(calcMac, mac);
    }
}
