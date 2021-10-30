package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.utils.FileUtils;

import java.io.File;

public abstract class BaseKeyPassOperation implements KeyPassOperation {
    private static final String KEY_PASS_FILENAME = "trousse.json";

    protected String mainPassword;

    protected BaseKeyPassOperation(String mainPassword) {
        this.mainPassword = mainPassword;
    }

    // TODO : Add javadoc
    protected KeyPass getKeyPass() {
        File keyPassFile = FileUtils.getOrCreateFile(KEY_PASS_FILENAME);
    }

    // TODO : Add javadoc
    protected void saveKeyPass(KeyPass keyPass) {
        // TODO : Save key pass
    }
}
