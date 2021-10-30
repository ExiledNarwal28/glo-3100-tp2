package ca.ulaval.glo3100.keypass;

public class AddKeyPassOperation extends BaseKeyPassOperation {
    private String url;
    private String user;
    private String password;

    public AddKeyPassOperation(String mainPassword, String url, String user, String password) {
        super(mainPassword);
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void execute() {
        KeyPassEntry entry = new KeyPassEntry(url, user, password);
        // TODO : Generate iv
        String iv = "";

        entry.encrypt(mainPassword, iv);
        KeyPass keyPass = getKeyPass();
        keyPass.add(entry);

        saveKeyPass(keyPass);
    }
}
