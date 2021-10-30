package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.FileUtils;
import ca.ulaval.glo3100.utils.KeyPassUtils;
import ca.ulaval.glo3100.utils.KeyUtils;

import javax.crypto.SecretKey;
import java.io.File;

public abstract class BaseKeyPassOperation implements KeyPassOperation {
    private static final String KEY_PASS_FILENAME = "trousse.json";
    protected static final String ENCRYPTED_STRING = "*****";

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

    // TODO : Add javadoc
    protected SecretKey getMainKey() {
        return KeyUtils.toSecretKey(mainPassword);
    }

    // TODO : Add javadoc
    protected void writeColumnNames() {
        writeRow("ligne", "url", "user", "password");
    }

    // TODO : Add javadoc
    protected void writeRow(String line, String url, String user, String password) {
        Logger.logInfo(String.format("%-10s %-10s %-10s %-10s", line, url, user, password));
    }
}
