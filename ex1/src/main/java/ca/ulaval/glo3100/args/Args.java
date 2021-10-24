package ca.ulaval.glo3100.args;

import java.io.File;
import java.util.List;

public class Args {
    public File directory;
    public List<FileType> fileTypes;
    public Operation operation;

    public Args(File directory, List<FileType> fileTypes, Operation operation) {
        this.directory = directory;
        this.fileTypes = fileTypes;
        this.operation = operation;
    }
}
