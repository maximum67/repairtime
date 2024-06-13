package com.example.repairtime.cipher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component
public class SecretKeyCipher {

    private static String CIPHER_KEY;

    @Value("${cipherKey}")
    public void setCipherKey(String cipherKey) {
        CIPHER_KEY = cipherKey;
    }

    public static String getCipherKey() {
        return CIPHER_KEY;
    }

    public static SecretKeySpec getKey(){
        return new SecretKeySpec(getCipherKey().getBytes(), "AES");
    }

}
