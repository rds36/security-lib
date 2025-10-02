package com.rds.securitylib.common.encrypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AesGcmEncryptor implements AesEncryptor {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 128;
    private final SecretKey key;
    private final SecureRandom random = new SecureRandom();

    public AesGcmEncryptor(byte[] rawKey){
        this.key = new SecretKeySpec(rawKey, "AES");
    }

    @Override
    public byte[] encrypt(byte[] plainText) {
        try {
            // generate random
            byte[] iv = new byte[12];
            random.nextBytes(iv);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(KEY_SIZE, iv));
            byte[] ct = c.doFinal(plainText);
            byte[] out = new byte[iv.length + ct.length];
            System.arraycopy(iv,0,out,0,iv.length);
            System.arraycopy(ct,0,out,iv.length,ct.length);
            return out;
        } catch(Exception e){ throw new IllegalStateException(e); }
    }

    @Override
    public byte[] decrypt(byte[] cipherText) {
        try {
            byte[] iv = new byte[12]; System.arraycopy(cipherText,0,iv,0,12);
            byte[] ct = new byte[cipherText.length-12]; System.arraycopy(cipherText,12,ct,0,ct.length);
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(KEY_SIZE, iv));
            return c.doFinal(ct);
        } catch(Exception e){
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}