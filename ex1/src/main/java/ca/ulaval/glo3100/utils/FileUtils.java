package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.args.FileType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

        // Get each files matching given file types
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
     * @return List of bytes for each file (filename and content)
     */
    public static List<byte[]> toBytes(List<File> files) {
        List<byte[]> fileAsBytes = new ArrayList<>();

        for (File file : files) {
            byte[] content;

            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("File %s could not be converted to bytes.", file.getPath()));
            }

            fileAsBytes.add(content);
        }

        return fileAsBytes;
    }

    /**
     * @param directory Directory to get or create file
     * @param filename Filename of file
     * @return existing or newly created file
     */
    public static File getOrCreateFile(File directory, String filename) {
        if (directory.isDirectory()) {
            return new File(directory, filename);
        } else {
            throw new IllegalArgumentException("Provided directory is not a directory");
        }
    }

    /**
     * @param files Files to delete
     */
    public static void deleteFiles(List<File> files) {
        for (File file : files) {
            boolean result = file.delete();

            if (!result) {
                throw new IllegalArgumentException(String.format("Could not delete file %s", file.getPath()));
            }
        }
    }

    /**
     * @param directory Directory to create new file
     * @param filename Filename of new file
     * @param fileAsStrings List of file as strings
     */
    public static void saveFilesAsStrings(File directory, String filename, List<String> fileAsStrings) {
        File file = getOrCreateFile(directory, filename);
        FileWriter fileWriter;

        try {
            // Open new file writer
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("FileWrite could not be instantiated with given directory and filename");
        }

        for (int i = 0; i < fileAsStrings.size(); i++) {
            try {
                fileWriter.write(fileAsStrings.get(i));
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not write to file");
            }

            // Add newline unless it's the last string
            if (i > fileAsStrings.size() - 1) {
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
    }

    /**
     * @param directory Directory to save files in
     * @param filesAsBytes Files as bytes to save
     */
    public static void saveFilesAsBytes(File directory, List<String> filenames, List<byte[]> filesAsBytes) {
        if (filenames.size() != filesAsBytes.size()) {
            throw new IllegalArgumentException("Size of given filenames and files as bytes do not match");
        }

        for (int i = 0; i < filesAsBytes.size(); i++) {
            File file = getOrCreateFile(directory, filenames.get(i));

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                // Write bytes to file
                fileOutputStream.write(filesAsBytes.get(i));
            } catch (IOException ioe) {
                throw new IllegalArgumentException("Could not write file");
            }
        }
    }

    /**
     * @param directory Directory to search for file in
     * @param filename Name of file containing each file as strings
     * @return Files as strings for given directory and filename
     */
    public static List<String> getFilesAsStrings(File directory, String filename) {
        File file = getOrCreateFile(directory, filename);

        // Get lines in file
        List<String> lines;
        try {
            lines = Files.lines(Paths.get(file.getPath())).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file");
        }

        return lines;
    }
}
