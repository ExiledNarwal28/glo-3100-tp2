package ca.ulaval.glo3100.args;

import java.util.HashMap;
import java.util.Map;

public enum FileType {
    XLS("xls"),
    DOC("doc"),
    PDF("pdf"),
    MP3("mp3"),
    AVI("avi"),
    txt("txt");

    private final String fileType;
    private static final Map<String, FileType> lookup = new HashMap<>();

    static {
        for (FileType fileType : FileType.values()) {
            lookup.put(fileType.toString(), fileType);
        }
    }

    FileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return fileType;
    }

    public static FileType get(String fileType) {
        if (fileType == null) throwInvalidFileType();

        FileType foundFileType = lookup.get(fileType.toLowerCase());

        if (foundFileType == null) throwInvalidFileType();

        return foundFileType;
    }

    private static void throwInvalidFileType() {
        throw new IllegalArgumentException("Invalid file type");
    }
}