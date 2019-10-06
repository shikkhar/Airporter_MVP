package com.example.airporter.helper;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MyHash {

    public static String sha256(String randomString)
    {
        MessageDigest digest = null;
        try
        {
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        byte[] hash = digest.digest(randomString.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder(hash.length * 2);
        for(byte b: hash)
            sb.append(String.format("%02x", b));
        return sb.toString().toUpperCase();
    }
}
