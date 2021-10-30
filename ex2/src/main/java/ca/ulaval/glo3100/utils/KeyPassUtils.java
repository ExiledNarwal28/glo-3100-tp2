package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.keypass.KeyPass;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class KeyPassUtils {

    /**
     * @param keyPassFile File to save key pass in
     * @param keyPass Key pass to save
     */
    public static void saveKeyPass(File keyPassFile, KeyPass keyPass) {
        try {
            // Write encryption params as JSON
            new ObjectMapper().writeValue(keyPassFile, keyPass);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not save to key pass file");
        }
    }

    /**
     * @param keyPassFile File where key pass is saved
     * @return Saved key pass
     */
    public static KeyPass getKeyPass(File keyPassFile) {
        // Return new key pass if file doesn't exist
        if (!keyPassFile.exists()) {
            return new KeyPass(new HashMap<>());
        }

        try {
            // Read encryption params from JSON
            return new ObjectMapper().readValue(keyPassFile, KeyPass.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read key pass file");
        }
    }
}
