package com.rds.securitylib.common.encrypt;

public interface AesEncryptor {
    byte[] encrypt(byte[] plainText);
    byte[] decrypt(byte[] cipherText);
}
