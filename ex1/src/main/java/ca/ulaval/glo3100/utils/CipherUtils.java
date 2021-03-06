package ca.ulaval.glo3100.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;

public class CipherUtils {

    private static final String TRANSFORMATION = "AES/CTR/NoPadding";
    private static final int INPUT_BUFFER_SIZE = 1024;

    /**
     * @param originalFile Original file to encrypt
     * @param key Key to encrypt file
     * @param iv IV to encrypt file
     */
    public static void encrypt(File originalFile, SecretKey key, IvParameterSpec iv) {
        // Get encrypted file (original file path + .enc)
        File encryptedFile = FileUtils.toEncryptedFile(originalFile);

        // Initialize needed streams
        try (FileInputStream input = new FileInputStream(originalFile);
             BufferedInputStream bufferedInput = new BufferedInputStream(input);
             FileOutputStream output = new FileOutputStream(encryptedFile);
             BufferedOutputStream bufferedOutput = new BufferedOutputStream(output)) {

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            // Write to buffer stream
            writeToStream(cipher, bufferedInput, bufferedOutput);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * @param encryptedFile Original file to decrypt
     * @param key Key to decrypt file
     * @param iv IV to decrypt file
     */
    public static void decrypt(File encryptedFile, SecretKey key, IvParameterSpec iv) {
        // Get original file (encrypted file path - .enc)
        File originalFile = FileUtils.toOriginalFile(encryptedFile);

        // Initialize needed streams
        try (FileInputStream input = new FileInputStream(encryptedFile);
             FileOutputStream output = new FileOutputStream(originalFile)) {

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            // Write to file output stream
            writeToStream(cipher, input, output);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * @param cipher Initialized cipher
     * @param input input stream of file
     * @param output output stream of file
     */
    private static void writeToStream(Cipher cipher, InputStream input, OutputStream output) {
        // Initialize input buffer and length
        byte[] inputBuffer = new byte[INPUT_BUFFER_SIZE];
        int length;

        try {
            // While we can read from input
            while ((length = input.read(inputBuffer)) != -1) {
                // Get out buffer with updated cipher
                byte[] outBuffer = cipher.update(inputBuffer, 0, length);

                if (outBuffer != null) {
                    // Write to output
                    output.write(outBuffer);
                }
            }

            // Get out buffer with finalized cipher
            byte[] outBuffer = cipher.doFinal();
            if (outBuffer != null) {
                // Write to output
                output.write(outBuffer);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
