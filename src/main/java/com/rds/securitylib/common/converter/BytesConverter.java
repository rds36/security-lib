package com.rds.securitylib.common.converter;

import java.nio.charset.StandardCharsets;

public class BytesConverter {
    public static String bytesToString(byte[] b){
        if(b==null) return null; return new String(b, StandardCharsets.UTF_8);
    }

    public static  byte[] stringToBytes(String s){
        if(s==null) return null; return s.getBytes(StandardCharsets.UTF_8);
    }
}
