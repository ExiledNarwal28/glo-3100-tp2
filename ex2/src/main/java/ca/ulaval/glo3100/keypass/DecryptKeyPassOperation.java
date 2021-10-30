package ca.ulaval.glo3100.keypass;

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
        // TODO : Execute decrypt operation
        KeyPass keyPass = getKeyPass();
        KeyPassEntry entry = keyPass.get(index);

        if (decryptUser) {
            entry.decryptUser(getMainKey());
        }

        if (decryptPassword) {
            entry.decryptPassword(getMainKey());
        }

        writeColumnNames();

        writeRow(index.toString(), entry.url, decryptUser ? entry.user : ENCRYPTED_STRING, decryptPassword ? entry.password : ENCRYPTED_STRING);
    }
}
