package com.rds.securitylib.encrypt;

public interface AesEncryptor {
    byte[] encrypt(byte[] plainText);
    byte[] decrypt(byte[] cipherText);
}
