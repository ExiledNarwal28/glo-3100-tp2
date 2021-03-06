package ca.ulaval.glo3100.utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class KeyUtils {

    private static final String SECRET_KEY_FACTORY_PARAM = "PBKDF2WithHmacSHA1";
    private static final String ENCRYPTION = "AES";
    private static final String SALT = "SUPER_SECRET_SALT";
    private static final int PBE_SPEC_PARAM = 100;
    private static final int KEY_SIZE = 128;
    private static final int IV_SIZE = 16;

    /**
     * @param password password as string
     * @return secret key built from password
     */
    public static SecretKey toSecretKey(String password) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_PARAM);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), PBE_SPEC_PARAM, KEY_SIZE);
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), ENCRYPTION);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create secret key");
        }
    }

    /**
     * @param iv iv as string
     * @return iv parameter spec built from iv as string
     */
    public static IvParameterSpec toIvParameterSpec(String iv) {
        return new IvParameterSpec(ByteUtils.toBytes(iv));
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
