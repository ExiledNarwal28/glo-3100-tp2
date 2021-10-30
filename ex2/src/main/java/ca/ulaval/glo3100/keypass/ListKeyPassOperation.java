package ca.ulaval.glo3100.keypass;

public class ListKeyPassOperation extends BaseKeyPassOperation {
    public ListKeyPassOperation(String mainPassword) {
        super(mainPassword);
    }

    @Override
    public void execute() {
        KeyPass keyPass = getKeyPass();

        // Decrypt the URL
        keyPass.entries.forEach((integer, keyPassEntry) -> keyPassEntry.decryptUrl(getMainKey()));

        // Write column names
        writeColumnNames();

        // Write each row
        keyPass.entries.forEach((integer, keyPassEntry) -> writeRow(integer.toString(), keyPassEntry.url, ENCRYPTED_STRING, ENCRYPTED_STRING));
    }
}
