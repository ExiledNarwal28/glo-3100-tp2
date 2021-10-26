package ca.ulaval.glo3100.utils;

import java.util.List;

public class EncryptionParams {
    public String key;
    public String iv;
    public List<String> filenames;

    // Default constructor needed to map from JSON files
    public EncryptionParams() {

    }

    public EncryptionParams(String key, String iv, List<String> filenames) {
        this.key = key;
        this.iv = iv;
        this.filenames = filenames;
    }
}
