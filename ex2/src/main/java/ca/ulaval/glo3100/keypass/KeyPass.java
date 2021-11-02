package ca.ulaval.glo3100.keypass;

import java.util.Map;

public class KeyPass {
    public Map<Integer, KeyPassEntry> entries;

    // Used to read from JSON
    public KeyPass() {

    }

    public KeyPass(Map<Integer, KeyPassEntry> entries) {
        this.entries = entries;
    }

    /**
     * @param entry entry to add to key pass
     */
    public void add(KeyPassEntry entry) {
        int index = entries.size() + 1;
        entries.put(index, entry);
    }

    /**
     * @param index index of entry to get
     * @return entry at given index
     */
    public KeyPassEntry get(int index) {
        KeyPassEntry entry = entries.get(index);

        if (entry == null) {
            throw new IllegalArgumentException("Index is not found in key pass.");
        }

        return entry;
    }
}
