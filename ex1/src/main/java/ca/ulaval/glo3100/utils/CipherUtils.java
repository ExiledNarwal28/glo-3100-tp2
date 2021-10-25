package ca.ulaval.glo3100.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.ArrayList;
import java.util.List;

public class CipherUtils {

    private static final String ENCRYPTION = "AES/CBC/PKCS5Padding";

    /**
     * @param originalFilesAsBytes Original files as bytes
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Encrypted files as bytes (filename + content)
     */
    public static List<FileAsBytes> encrypt(List<FileAsBytes> originalFilesAsBytes, SecretKey key, IvParameterSpec iv) {
        return applyEncryption(Cipher.ENCRYPT_MODE, originalFilesAsBytes, key, iv);
    }

    /**
     * @param encryptedFilesAsBytes Encrypted files as bytes
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Original files as bytes (filename + content)
     */
    public static List<FileAsBytes> decrypt(List<FileAsBytes> encryptedFilesAsBytes, SecretKey key, IvParameterSpec iv) {
        return applyEncryption(Cipher.DECRYPT_MODE, encryptedFilesAsBytes, key, iv);
    }

    /**
     * @param cipherMode Cipher mode (encrypt or decrypt)
     * @param originalFilesAsBytes Original files as bytes
     * @param key Encryption key
     * @param iv Encryption IV
     * @return Encrypted files as bytes (filename + content)
     */
    private static List<FileAsBytes> applyEncryption(int cipherMode, List<FileAsBytes> originalFilesAsBytes, SecretKey key, IvParameterSpec iv) {
        Cipher cipher;

        try {
            // Create a new Cipher instance with needed encryption
            cipher = Cipher.getInstance(ENCRYPTION);

            // Initiate Cipher with given key and iv
            cipher.init(cipherMode, key, iv);

            List<FileAsBytes> encryptedFilesAsBytes = new ArrayList<>();

            // Encrypt each bytes with cipher
            for (FileAsBytes originalFileAsBytes : originalFilesAsBytes) {
                byte[] filename = cipher.doFinal(originalFileAsBytes.filename);
                byte[] content = cipher.doFinal(originalFileAsBytes.content);

                encryptedFilesAsBytes.add(new FileAsBytes(filename, content));
            }

            return encryptedFilesAsBytes;
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Encryption %s was not found", ENCRYPTION));
        }
    }
}
