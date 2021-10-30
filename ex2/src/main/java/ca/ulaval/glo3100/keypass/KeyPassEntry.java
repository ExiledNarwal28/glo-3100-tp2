package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.utils.CipherUtils;
import ca.ulaval.glo3100.utils.KeyUtils;

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

    // TODO : Add javadoc
    public void encrypt(SecretKey key, IvParameterSpec iv) {
        this.user = CipherUtils.encrypt(this.user, key, iv);
        this.password = CipherUtils.encrypt(this.password, key, iv);
        this.iv = iv.toString();
    }

    // TODO : Add javadoc
    public void decrypt(SecretKey key) {
        IvParameterSpec iv = KeyUtils.toIvParameterSpec(this.iv);
        this.user = CipherUtils.decrypt(this.user, key, iv);
        this.password = CipherUtils.decrypt(this.password, key, iv);
    }
}
