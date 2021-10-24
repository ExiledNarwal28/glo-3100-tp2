package ca.ulaval.glo3100.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.util.Base64;

public class JsonUtils {

    // TODO : Add javadoc
    public static void saveEncryptionParams(File directory, String filename, SecretKey key, IvParameterSpec iv) {
        EncryptionParams encryptionParams = new EncryptionParams(
                Base64.getEncoder().encodeToString(key.getEncoded()),
                Base64.getEncoder().encodeToString(iv.getIV())
        );

        // TODO : Rest of this
    }
}
