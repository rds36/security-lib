package com.rds.securitylib.common.converter;

import java.nio.charset.StandardCharsets;

public class BytesConverter {
    public static String bytesToString(byte[] b){
        if(b==null) return null; return new String(b, StandardCharsets.UTF_8);
    }
}
