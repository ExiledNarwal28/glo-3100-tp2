package ca.ulaval.glo3100.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class JsonFileUtils {

    /**
     * @param directory Directory to write file in
     * @param filename Filename of new file
     * @param key Key to save
     * @param iv IV to save
     */
    public static void saveEncryptionParams(File directory, String filename, SecretKey key, IvParameterSpec iv) {
        // Created encryption params
        EncryptionParams encryptionParams = new EncryptionParams(
                Base64.getEncoder().encodeToString(key.getEncoded()),
                Base64.getEncoder().encodeToString(iv.getIV())
        );

        // Create file to save params
        File file = FileUtils.getOrCreateFile(directory, filename);

        // Write encryption params as JSON
        try {
            new ObjectMapper().writeValue(file, encryptionParams);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not save encryption params");
        }
    }

    /**
     * @param directory Directory to read file in
     * @param filename Filename of file
     * @return encryption params (key and iv)
     */
    public static EncryptionParams getEncryptionParams(File directory, String filename) {
        // Get file for params
        File file = FileUtils.getOrCreateFile(directory, filename);

        // Read encryption params from JSON
        try {
            return new ObjectMapper().readValue(file, EncryptionParams.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read encryption params");
        }
    }
}
