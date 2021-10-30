package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.utils.KeyUtils;

import javax.crypto.spec.IvParameterSpec;

public class AddKeyPassOperation extends BaseKeyPassOperation {
    private final String url;
    private final String user;
    private final String password;

    public AddKeyPassOperation(String mainPassword, String url, String user, String password) {
        super(mainPassword);
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void execute() {
        // Instantiate entry
        KeyPassEntry entry = new KeyPassEntry(url, user, password);

        // Generate iv and encrypt entry
        IvParameterSpec iv = KeyUtils.generateIv();
        entry.encrypt(getMainKey(), iv);

        // Get key pass and add entry
        KeyPass keyPass = getKeyPass();
        keyPass.add(entry);

        // Save key pass with new entry
        saveKeyPass(keyPass);
    }
}
