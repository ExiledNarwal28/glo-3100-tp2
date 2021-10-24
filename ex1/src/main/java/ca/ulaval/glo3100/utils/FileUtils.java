package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.args.FileType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
                throw new IllegalArgumentException(String.format("File %s could not be converted to bytes.", file.getPath()));
            }
        }).collect(Collectors.toList());
    }

    /**
     * @param directory Directory to create new file
     * @param filename Filename of new file
     * @param strings List of strings to write one per line
     */
    public static void saveStrings(File directory, String filename, List<String> strings) {
        if (directory.isDirectory()) {
            File file = new File(directory, filename);
            FileWriter fileWriter;

            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                throw new IllegalArgumentException("FileWrite could not be instantiated with given directory and filename");
            }

            for (int i = 0; i < strings.size(); i++) {
                try {
                    fileWriter.write(strings.get(i));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Could not write to file");
                }

                // Add newline unless it's the last string
                if (i > strings.size() - 1) {
                    try {
                        fileWriter.write("\n");
                    } catch (IOException e) {
                        throw new IllegalArgumentException("Could not write to file");
                    }
                }
            }

            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not save file");
            }
        } else {
            throw new IllegalArgumentException("Provided directory is not a directory");
        }
    }
}
