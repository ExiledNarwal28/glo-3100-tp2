package ca.ulaval.glo3100.keypass;

public class KeyPassEntry {
    private String url;
    private String user;
    private String password;
    private String iv;

    public KeyPassEntry(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void encrypt(String key, String iv) {
        // TODO : Encrypt user
        // TODO : Encrypt password
        this.iv = iv;
    }

    public void decrypt(String key, String iv) {
        // TODO : Decrypt user
        // TODO : Decrypt password
    }
}
