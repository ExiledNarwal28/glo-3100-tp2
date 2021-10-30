package ca.ulaval.glo3100.utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class KeyUtils {

    private static final String ENCRYPTION = "AES";
    private static final String SALT = "SUPER_SECRET_SALT";
    private static final int IV_SIZE = 16;

    // TODO : Cleanup
    // TODO : Add javadoc
    public static SecretKey toSecretKey(String password) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), ENCRYPTION);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create secret key");
        }
    }

    // TODO : IV seems wrong, decryption does not work
    // TODO : Add javadoc
    public static IvParameterSpec toIvParameterSpec(String ivAsString) {
        byte[] iv = ByteUtils.toBytes(ivAsString);
        return new IvParameterSpec(iv);
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
