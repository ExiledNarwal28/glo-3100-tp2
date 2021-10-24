package ca.ulaval.glo3100.utils;

public class FileAsBytes {
    public byte[] filename;
    public byte[] content;

    public FileAsBytes(byte[] filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }
}
