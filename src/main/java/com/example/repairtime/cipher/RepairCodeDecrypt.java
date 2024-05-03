package com.example.repairtime.cipher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RepairCodeDecrypt {
    public static String repairCodeDecryptCipher(String encodedString) throws NoSuchPaddingException,
                                                                              NoSuchAlgorithmException,
                                                                              IllegalBlockSizeException,
                                                                              BadPaddingException,
                                                                              InvalidKeyException {
        Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, SecretKeyCipher.getKey());
        return new String(decryptCipher.doFinal(Base64.getDecoder().decode(encodedString)), StandardCharsets.UTF_8);
    }
}
