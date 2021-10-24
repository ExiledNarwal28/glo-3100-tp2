package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.args.FileType;
import ca.ulaval.glo3100.console.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * @param files Files to convert to bytes
     * @return List of bytes for each files
     */
    public static List<byte[]> toBytes(List<File> files) {
        return files.stream().map(file -> {
            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                Logger.logInfo(String.format("File %s could not be converted to bytes.", file.getPath()));
                return new byte[] {};
            }
        }).collect(Collectors.toList());
    }
}
