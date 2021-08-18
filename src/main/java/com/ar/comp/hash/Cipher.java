package com.ar.comp.hash;

public class Cipher {

    static public String decrypt(String ciphertext)
    {
        return com.coopcoder.getcoder.Cipher.authcode(ciphertext, "DECODE", "");
    }

    static public String encrypt(String ciphertext)
    {
        return com.coopcoder.getcoder.Cipher.authcode(ciphertext, "ENCODE", "");
    }
}
