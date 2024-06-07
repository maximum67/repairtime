package com.example.repairtime.cipher;

import javax.crypto.spec.SecretKeySpec;

public class SecretKeyCipher {
    public static SecretKeySpec getKey(){
        return new SecretKeySpec("testtesttesttest".getBytes(), "AES");
    }

}
