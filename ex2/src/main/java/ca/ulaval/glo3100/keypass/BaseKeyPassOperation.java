package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.utils.FileUtils;
import ca.ulaval.glo3100.utils.KeyPassUtils;
import ca.ulaval.glo3100.utils.KeyUtils;

import javax.crypto.SecretKey;
import java.io.File;

public abstract class BaseKeyPassOperation implements KeyPassOperation {
    private static final String KEY_PASS_FILENAME = "trousse.json";

    private final String mainPassword;

    protected BaseKeyPassOperation(String mainPassword) {
        this.mainPassword = mainPassword;
    }

    // TODO : Add javadoc
    protected KeyPass getKeyPass() {
        File keyPassFile = FileUtils.getOrCreateFile(KEY_PASS_FILENAME);
        return KeyPassUtils.getKeyPass(keyPassFile);
    }

    // TODO : Add javadoc
    protected void saveKeyPass(KeyPass keyPass) {
        File keyPassFile = FileUtils.getOrCreateFile(KEY_PASS_FILENAME);
        KeyPassUtils.saveKeyPass(keyPassFile, keyPass);
    }

    protected SecretKey getMainKey() {
        return KeyUtils.toSecretKey(mainPassword);
    }
}
