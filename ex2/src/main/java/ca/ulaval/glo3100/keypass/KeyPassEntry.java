package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.utils.CipherUtils;
import ca.ulaval.glo3100.utils.KeyUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class KeyPassEntry {
    public String url;
    public String user;
    public String password;
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
    public void decryptUser(SecretKey key) {
        this.user = CipherUtils.decrypt(this.user, key, getIvParameterSpec());
    }

    // TODO : Add javadoc
    public void decryptPassword(SecretKey key) {
        this.password = CipherUtils.decrypt(this.password, key, getIvParameterSpec());
    }

    private IvParameterSpec getIvParameterSpec() {
        return KeyUtils.toIvParameterSpec(this.iv);
    }
}
