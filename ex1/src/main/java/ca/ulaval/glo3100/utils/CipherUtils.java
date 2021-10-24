package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.console.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.ArrayList;
import java.util.List;

public class CipherUtils {

    private static final String ENCRYPTION = "AES/CBC/PKCS5Padding";

    /**
     * @param originalBytes Original bytes to encrypted
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Encrypted bytes
     */
    public static List<byte[]> encrypt(List<byte[]> originalBytes, SecretKey key, IvParameterSpec iv) {
        Cipher cipher;

        try {
            // Create a new Cipher instance with needed encryption
            cipher = Cipher.getInstance(ENCRYPTION);

            // Initiate Cipher with given key and iv
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            List<byte[]> encryptedBytes = new ArrayList<>();

            // Encrypt each bytes with cipher
            for (byte[] originalByte : originalBytes) {
                encryptedBytes.add(cipher.doFinal(originalByte));
            }

            return encryptedBytes;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logDebug(String.format("Encryption %s was not found", ENCRYPTION));
            return new ArrayList<>();
        }
    }
}
