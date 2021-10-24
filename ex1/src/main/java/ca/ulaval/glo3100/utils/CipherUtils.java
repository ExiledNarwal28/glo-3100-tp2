package ca.ulaval.glo3100.utils;

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
     * @return Encrypted bytes for files (filename + content)
     */
    public static List<FileAsBytes> encrypt(List<FileAsBytes> originalBytes, SecretKey key, IvParameterSpec iv) {
        Cipher cipher;

        try {
            // Create a new Cipher instance with needed encryption
            cipher = Cipher.getInstance(ENCRYPTION);

            // Initiate Cipher with given key and iv
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            List<FileAsBytes> encryptedBytes = new ArrayList<>();

            // Encrypt each bytes with cipher
            for (FileAsBytes originalFile : originalBytes) {
                byte[] filename = cipher.doFinal(originalFile.filename);
                byte[] content = cipher.doFinal(originalFile.content);

                encryptedBytes.add(new FileAsBytes(filename, content));
            }

            return encryptedBytes;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Encryption %s was not found", ENCRYPTION));
        }
    }
}
