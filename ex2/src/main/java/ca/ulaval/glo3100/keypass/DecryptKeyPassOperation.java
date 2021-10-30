package ca.ulaval.glo3100.keypass;

import javax.crypto.SecretKey;

public class DecryptKeyPassOperation extends BaseKeyPassOperation {
    private final Integer index;
    private final boolean decryptUser;
    private final boolean decryptPassword;

    public DecryptKeyPassOperation(String mainPassword, int index, boolean decryptUser, boolean decryptPassword) {
        super(mainPassword);
        this.index = index;
        this.decryptUser = decryptUser;
        this.decryptPassword = decryptPassword;
    }

    @Override
    public void execute() {
        // Get key pass entry in key pass
        KeyPass keyPass = getKeyPass();
        // TODO : If index does not exist?
        KeyPassEntry entry = keyPass.get(index);

        // Get main password as secret key
        SecretKey mainKey = getMainKey();

        // Always decrypt URL
        entry.decryptUrl(mainKey);

        // Decrypt user, if needed
        if (decryptUser) {
            entry.decryptUser(mainKey);
        }

        // Decrypt password, if needed
        if (decryptPassword) {
            entry.decryptPassword(mainKey);
        }

        // Write column names
        writeColumnNames();

        // Write row for entry
        writeRow(index.toString(), entry.url, decryptUser ? entry.user : ENCRYPTED_STRING, decryptPassword ? entry.password : ENCRYPTED_STRING);
    }
}
