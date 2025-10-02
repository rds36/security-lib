package com.rds.securitylib.common.masking;

import java.nio.charset.StandardCharsets;

public class MaskGenerator {

    public static String mask(String s){
        if(s==null || s.length()<4) return "***"; return "***-***-"+s.substring(s.length()-4);
    }
    public static String maskAddress(String s){
        if(s==null) return null; return s.length() > 12 ? s.substring(0, 8)+"***" : "***";
    }
}
