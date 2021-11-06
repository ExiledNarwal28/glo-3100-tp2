package ca.ulaval.glo3100.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;

public class CipherUtils {

    private static final String TRANSFORMATION = "AES/CTR/NoPadding";

    // TODO : Add javadocs
    public static void encrypt(File originalFile, SecretKey key, IvParameterSpec iv) {
        // TODO : Move this to FileUtils
        File encryptedFile = new File(String.format("%s.enc", originalFile.getPath()));

        try (FileInputStream fis = new FileInputStream(originalFile);
             BufferedInputStream in = new BufferedInputStream(fis);
             FileOutputStream out = new FileOutputStream(encryptedFile);
             BufferedOutputStream bos = new BufferedOutputStream(out)) {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] inBuffer = new byte[1024];
            int len;
            while ((len = in.read(inBuffer)) != -1) {
                byte[] outBuffer = cipher.update(inBuffer, 0, len);
                if (outBuffer != null) {
                    bos.write(outBuffer);
                }
            }

            byte[] outBuffer = cipher.doFinal();
            if (outBuffer != null) {
                bos.write(outBuffer);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // TODO : Add javadocs
    public static void decrypt(File encryptedFile, SecretKey key, IvParameterSpec iv) {
        // TODO : Move this to FileUtils
        File originalFile = new File(encryptedFile.getAbsolutePath().substring(0, encryptedFile.getAbsolutePath().length() - 4));

        try (FileInputStream in = new FileInputStream(encryptedFile);
             FileOutputStream out = new FileOutputStream(originalFile)) {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            byte[] inBuffer = new byte[1024];
            int len;

            while ((len = in.read(inBuffer)) != -1) {
                byte[] outBuffer = cipher.update(inBuffer, 0, len);
                if (outBuffer != null) {
                    out.write(outBuffer);
                }
            }

            byte[] outBuffer = cipher.doFinal();
            if (outBuffer != null) {
                out.write(outBuffer);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
