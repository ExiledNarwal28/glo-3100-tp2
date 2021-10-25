package ca.ulaval.glo3100.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionParamsUtils {

    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final int IV_SIZE = 16;

    public static SecretKey toSecretKey(String keyAsString) {
        byte[] key = Base64.getDecoder().decode(keyAsString);
        return new SecretKeySpec(key, 0, key.length, ENCRYPTION_ALGORITHM);
    }

    public static IvParameterSpec toIvParameterSpec(String ivAsString) {
        byte[] iv = Base64.getDecoder().decode(ivAsString);
        return new IvParameterSpec(iv);
    }

    /**
     * @return Newly randomly generated key
     */
    public static SecretKey generateKey() {
        KeyGenerator keyGen;

        try {
            keyGen = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(String.format("%s was not found in KeyGenerator.", ENCRYPTION_ALGORITHM));
        }

        // Initialize key length
        keyGen.init(KEY_SIZE);

        // Return newly generated key
        return keyGen.generateKey();
    }

    /**
     * @return Newly randomly generated IV
     */
    public static IvParameterSpec generateIv() {
        // Create new instance of secure random generator
        SecureRandom random = new SecureRandom();

        // Generate random iv
        byte[] iv0 = random.generateSeed(IV_SIZE);

        return new IvParameterSpec(iv0);
    }
}
