package ca.ulaval.glo3100.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CipherUtils {

    private static final String TRANSFORMATION = "AES/CTR/NoPadding";

    /**
     * @param originalBytes Original bytes
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Encrypted bytes
     */
    public static byte[] encrypt(byte[] originalBytes, SecretKey key, IvParameterSpec iv) {
        return applyEncryption(Cipher.ENCRYPT_MODE, originalBytes, key, iv);
    }

    /**
     * @param encryptedBytes Encrypted bytes
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Original bytes
     */
    public static byte[] decrypt(byte[] encryptedBytes, SecretKey key, IvParameterSpec iv) {
        return applyEncryption(Cipher.DECRYPT_MODE, encryptedBytes, key, iv);
    }

    /**
     * @param cipherMode Cipher mode (encrypt or decrypt)
     * @param originalBytes Original bytes
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Encrypted bytes
     */
    private static byte[] applyEncryption(int cipherMode, byte[] originalBytes, SecretKey key, IvParameterSpec iv) {
        Cipher cipher;

        try {
            // Create a new Cipher instance with needed encryption
            cipher = Cipher.getInstance(TRANSFORMATION);

            // Initiate Cipher with given key and iv
            cipher.init(cipherMode, key, iv);

            // Encrypt bytes
            return cipher.doFinal(originalBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Encryption %s was not found", TRANSFORMATION));
        }
    }
}
