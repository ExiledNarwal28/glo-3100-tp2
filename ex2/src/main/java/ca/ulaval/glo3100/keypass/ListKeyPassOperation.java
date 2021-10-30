package ca.ulaval.glo3100.keypass;

public class ListKeyPassOperation extends BaseKeyPassOperation {
    public ListKeyPassOperation(String mainPassword) {
        super(mainPassword);
    }

    // TODO : Now, why don't we need the main password here?
    @Override
    public void execute() {
        KeyPass keyPass = getKeyPass();

        writeColumnNames();

        keyPass.entries.forEach((integer, keyPassEntry) -> writeRow(integer.toString(), keyPassEntry.url, ENCRYPTED_STRING, ENCRYPTED_STRING));
    }
}
