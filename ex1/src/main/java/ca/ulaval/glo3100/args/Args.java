package ca.ulaval.glo3100.args;

import java.util.List;

public class Args {
    public String directory;
    public List<FileType> fileTypes;
    public Operation operation;

    public Args(String directory, List<FileType> fileTypes, Operation operation) {
        this.directory = directory;
        this.fileTypes = fileTypes;
        this.operation = operation;
    }
}
