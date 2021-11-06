package ca.ulaval.glo3100.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CipherUtils {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * @param originalString original string
     * @param key Key used to encrypt string
     * @param iv IV used to encrypt string
     * @return encrypted string
     */
    public static String encryptString(String originalString, SecretKey key, IvParameterSpec iv) {
        byte[] originalBytes = originalString.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = CipherUtils.encrypt(originalBytes, key, iv);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * @param originalString original URL
     * @param key Key used to encrypt URL
     * @param iv IV used to encrypt URL
     * @return encrypted URL
     */
    public static String encryptUrlString(String originalString, SecretKey key, IvParameterSpec iv) {
        String base64String = Base64.getUrlEncoder().encodeToString(originalString.getBytes(StandardCharsets.UTF_8));
        byte[] originalBytes = base64String.getBytes();
        byte[] encryptedBytes = CipherUtils.encrypt(originalBytes, key, iv);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * @param encryptedString encrypted string
     * @param key Key used to encrypt string
     * @param iv IV used to encrypt string
     * @return encrypted string
     */
    public static String decryptString(String encryptedString, SecretKey key, IvParameterSpec iv) {
        byte[] encryptedBytes = encryptedString.getBytes();
        byte[] originalBytes = CipherUtils.encrypt(encryptedBytes, key, iv);
        return new String(originalBytes);
    }

    /**
     * @param encryptedString encrypted URL
     * @param key Key used to encrypt URL
     * @param iv IV used to encrypt URL
     * @return original URL
     */
    public static String decryptUrlString(String encryptedString, SecretKey key, IvParameterSpec iv) {
        byte[] encryptedBytes = encryptedString.getBytes();
        byte[] originalBytes = CipherUtils.decrypt(encryptedBytes, key, iv);
        return new String(originalBytes);
    }

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

            // Encrypt original bytes
            return cipher.doFinal(originalBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
