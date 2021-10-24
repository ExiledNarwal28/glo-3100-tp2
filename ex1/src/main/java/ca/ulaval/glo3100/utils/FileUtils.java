package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.args.FileType;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    /**
     * @param stringDirectory directory as string
     * @return Directory as File type
     */
    public static File getDirectory(String stringDirectory) {
        File directory;

        try {
            directory = new File(stringDirectory);
        } catch(Exception e) {
            throw new IllegalArgumentException("Provided directory is invalid");
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Provided directory is not a directory");
        }

        return directory;
    }

    /**
     * @param directory Directory to look for files
     * @param fileTypes Accepted file types
     * @return List of files in directory that match given files types
     */
    public static List<File> getFiles(File directory, List<FileType> fileTypes) {
        List<File> files = new ArrayList<>();

        File[] filesArray = directory.listFiles((dir, name) -> {
            for (FileType fileType : fileTypes) {
                if (name.endsWith(String.format(".%s", fileType))) {
                    return true;
                }
            }

            return false;
        });

        if (filesArray != null) {
            files.addAll(Arrays.asList(filesArray));
        }

        return files;
    }
}
