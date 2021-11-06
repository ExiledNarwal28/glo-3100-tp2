package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.utils.ByteUtils;
import ca.ulaval.glo3100.utils.CipherUtils;
import ca.ulaval.glo3100.utils.KeyUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

    /**
     * @param key Key used to encrypt values
     * @param iv IV used to encrypt values
     */
    public void encrypt(SecretKey key, IvParameterSpec iv) {
        // TODO : Encrypt URL
        // this.url = CipherUtils.encryptUrlString(this.url, key, iv);
        this.user = CipherUtils.encryptString(this.user, key, iv);
        this.password = CipherUtils.encryptString(this.password, key, iv);
        this.iv = ByteUtils.toString(iv.getIV());
    }

    /**
     * @param key Key used to decrypt URL
     */
    public void decryptUrl(SecretKey key) {
        // TODO : Decrypt URL
        // this.url = CipherUtils.decryptUrlString(this.url, key, getIvParameterSpec());
    }

    /**
     * @param key Key used to decrypt user
     */
    public void decryptUser(SecretKey key) {
        this.user = CipherUtils.decryptString(this.user, key, getIvParameterSpec());
    }

    /**
     * @param key Key used to decrypt password
     */
    public void decryptPassword(SecretKey key) {
        this.password = CipherUtils.decryptString(this.password, key, getIvParameterSpec());
    }

    private IvParameterSpec getIvParameterSpec() {
        return KeyUtils.toIvParameterSpec(this.iv);
    }
}
