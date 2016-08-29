package com.websystique.springmvc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by arkadutta on 17/08/16.
 */
public class Util {

    public static String encrypt(String key){
        String algorithm = "SHA";

        byte[] plainText = key.getBytes();

        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        md.reset();
        md.update(plainText);
        byte[] encodedPassword = md.digest();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encodedPassword.length; i++) {
            if ((encodedPassword[i] & 0xff) < 0x10) {
                sb.append("0");
            }

            sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return sb.toString();
    }

    public static String generateSession(){
        synchronized (Util.class){
            return UUID.randomUUID().toString();
        }
    }

    public static String convertToJSON(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            str = mapper.writeValueAsString(obj);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }

        return str;
    }

    public static Object convertToObject(Class aClass,String json){
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try{
            obj = mapper.readValue(json, aClass);
        }catch(IOException e){
            e.printStackTrace();
        }

        return obj;

    }
}
