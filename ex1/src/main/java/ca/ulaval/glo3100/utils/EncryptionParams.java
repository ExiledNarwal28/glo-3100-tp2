package ca.ulaval.glo3100.utils;

public class EncryptionParams {
    public String key;
    public String iv;

    // Default constructor needed to map from JSON files
    public EncryptionParams() {

    }

    public EncryptionParams(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }
}
