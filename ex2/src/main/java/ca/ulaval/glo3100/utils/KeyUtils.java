package ca.ulaval.glo3100.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class KeyUtils {

    private static final String ENCRYPTION = "AES";
    private static final int IV_SIZE = 16;

    // TODO : Add javadoc
    public static SecretKey toSecretKey(String keyAsString) {
        byte[] key = ByteUtils.toBytes(keyAsString);
        return new SecretKeySpec(key, ENCRYPTION);
    }

    // TODO : Remove if not needed
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
