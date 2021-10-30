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

    // TODO : Add javadoc
    public void add(KeyPassEntry entry) {
        int index = entries.size() + 1;
        entries.put(index, entry);
    }

    public KeyPassEntry get(int index) {
        return entries.get(index);
    }
}
