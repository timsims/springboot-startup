package com.bootstrap.startup.components.encryption;

/**
 * 数据加密接口
 *
 * @author timsims
 */
public interface Encrypter {
    /**
     * 加密数据
     *
     * @param value 待加密的原始数据
     * @return 加密后的数据
     */
    String encrypt(String value);

    /**
     * 解密报文
     *
     * @param payload 待解密的数据
     * @return 解密后的原始数据
     */
    String decrypt(String payload);
}
