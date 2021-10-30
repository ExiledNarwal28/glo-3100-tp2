package ca.ulaval.glo3100.keypass;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

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

    public void encrypt(SecretKey key, IvParameterSpec iv) {
        // TODO : Encrypt user
        // TODO : Encrypt password
        this.iv = iv.toString();
    }

    public void decrypt(String key, String iv) {
        // TODO : Decrypt user
        // TODO : Decrypt password
    }
}
