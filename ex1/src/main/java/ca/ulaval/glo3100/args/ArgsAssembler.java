package ca.ulaval.glo3100.args;

import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArgsAssembler {

    private static final String DIRECTORY_ARG = "-d";
    private static final String FILE_TYPE_ARG = "-f";
    private static final String OPERATION_ARG = "-op";
    private static final String DEBUG_ARG = "-debug";

    public static Args assemble(String[] args) {
        // Default values
        String stringDirectory = System.getProperty("user.dir"); // Default directory is current directory
        List<FileType> fileTypes = new ArrayList<>();
        Operation operation = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case DIRECTORY_ARG:
                    stringDirectory = args[i + 1];
                    break;
                case FILE_TYPE_ARG:
                    fileTypes.add(FileType.get(args[i + 1]));
                    break;
                case OPERATION_ARG:
                    operation = Operation.get(args[i + 1]);
                    break;
                case DEBUG_ARG:
                    Logger.isDebugging = true;
                    break;
            }
        }

        if (operation == null) {
            throw new IllegalArgumentException("Provide operation with -op (enc or dec).");
        }

        if (operation == Operation.ENCRYPT) {
            if (fileTypes.isEmpty()) {
                throw new IllegalArgumentException("Provide file types with -f. Ex. : 'ex1 -f doc -f xls -op enc");
            }
        } else if (operation == Operation.DECRYPT) {
            if (!fileTypes.isEmpty()) {
                throw new IllegalArgumentException("No file types should be provided when decrypting");
            }
        }

        // Converting string directory to actual directory
        File directory = FileUtils.getDirectory(stringDirectory);

        return new Args(directory, fileTypes, operation);
    }
}