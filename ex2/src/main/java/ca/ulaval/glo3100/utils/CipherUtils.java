package ca.ulaval.glo3100.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.ArrayList;
import java.util.List;

public class CipherUtils {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * @param string Original string
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Encrypted string
     */
    public static String encrypt(String string, SecretKey key, IvParameterSpec iv) {
        return applyEncryption(Cipher.ENCRYPT_MODE, string, key, iv);
    }

    /**
     * @param string Encrypted string
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Original string
     */
    public static String decrypt(String string, SecretKey key, IvParameterSpec iv) {
        return applyEncryption(Cipher.DECRYPT_MODE, string, key, iv);
    }

    /**
     * @param cipherMode Cipher mode (encrypt or decrypt)
     * @param string Original string
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Encrypted string
     */
    private static String applyEncryption(int cipherMode, String string, SecretKey key, IvParameterSpec iv) {
        Cipher cipher;

        try {
            // Create a new Cipher instance with needed encryption
            cipher = Cipher.getInstance(TRANSFORMATION);

            // Initiate Cipher with given key and iv
            cipher.init(cipherMode, key, iv);

            byte[] originalBytes = ByteUtils.toBytes(string);
            byte[] encryptedBytes = cipher.doFinal(originalBytes);

            return ByteUtils.toString(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Encryption %s was not found", TRANSFORMATION));
        }
    }
}
