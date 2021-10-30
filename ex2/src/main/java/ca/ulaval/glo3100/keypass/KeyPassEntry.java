package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.utils.ByteUtils;
import ca.ulaval.glo3100.utils.CipherUtils;
import ca.ulaval.glo3100.utils.KeyUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class KeyPassEntry {
    public String url;
    public String user;
    public String password;
    public String iv;

    // Used to read from JSON
    public KeyPassEntry() {

    }

    public KeyPassEntry(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // TODO : Add javadoc
    public void encrypt(SecretKey key, IvParameterSpec iv) {
        this.url = CipherUtils.encrypt(this.url, key, iv);
        this.user = CipherUtils.encrypt(this.user, key, iv);
        this.password = CipherUtils.encrypt(this.password, key, iv);
        this.iv = ByteUtils.toString(iv.getIV());
    }

    // TODO : Add javadoc
    public void decryptUrl(SecretKey key) {
        this.url = decrypt(key, this.url);
    }

    // TODO : Add javadoc
    public void decryptUser(SecretKey key) {
        this.user = decrypt(key, this.user);
    }

    // TODO : Add javadoc
    public void decryptPassword(SecretKey key) {
        this.password = decrypt(key, this.password);
    }

    private String decrypt(SecretKey key, String encryptedString) {
        return CipherUtils.decrypt(encryptedString, key, getIvParameterSpec());
    }

    private IvParameterSpec getIvParameterSpec() {
        return KeyUtils.toIvParameterSpec(this.iv);
    }
}
