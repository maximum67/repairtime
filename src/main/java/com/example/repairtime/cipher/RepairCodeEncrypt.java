package com.example.repairtime.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RepairCodeEncrypt {
    public static String repairCodeCryptCipher(String input) throws NoSuchPaddingException,
                                                                    NoSuchAlgorithmException,
                                                                    InvalidKeyException,
                                                                    IllegalBlockSizeException,
                                                                    BadPaddingException {
        Cipher cryptCipher = Cipher.getInstance("AES");
        cryptCipher.init(Cipher.ENCRYPT_MODE, SecretKeyCipher.getKey());
        return Base64.getEncoder().encodeToString(cryptCipher.doFinal(input.getBytes(StandardCharsets.UTF_8)));
    }
}
