package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.args.FileType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

        File[] filesInDirectory = directory.listFiles();

        // If no files, return empty list
        if (filesInDirectory == null) {
            return files;
        }

        for (File file : filesInDirectory) {
            // Add file if it matches given filetypes
            for (FileType fileType : fileTypes) {
                if (file.getName().endsWith(String.format(".%s", fileType))) {
                    files.add(file);
                    break;
                }
            }
        }

        return files;
    }

    /**
     * @param directory Directory to look for subdirectories
     * @return List of subdirectories in directory
     */
    public static List<File> getSubdirectories(File directory) {
        List<File> subdirectories = new ArrayList<>();

        File[] filesInDirectory = directory.listFiles();

        // If no files, return empty list
        if (filesInDirectory == null) {
            return subdirectories;
        }

        for (File file : filesInDirectory) {
            // Add if file is a directory
            if (file != directory && file.isDirectory()) {
                subdirectories.add(file);
            }
        }

        return subdirectories;
    }

    /**
     * @param file File to convert to bytes
     * @return Bytes for file
     */
    public static byte[] toBytes(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("File %s could not be converted to bytes.", file.getPath()));
        }
    }

    /**
     * @param directory Directory to get or create file
     * @param filePath Path of file
     * @return existing or newly created file
     */
    public static File getOrCreateFile(File directory, String filePath) {
        // Create directory if it does not exist
        if (!directory.exists()) {
            try {
                Files.createDirectories(Paths.get(directory.getAbsolutePath()));
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not create directory");
            }
        }

        // Get each directory and file in path
        Path path = Paths.get(filePath);
        List<String> files = StreamSupport.stream(path.spliterator(), false).map(Path::toString).collect(Collectors.toList());

        // If there are subdirectories, create recursively
        if (files.size() > 1) {
            File subdirectory = new File(directory, files.get(0));
            return getOrCreateFile(subdirectory, files.get(1));
        }

        File file = new File(directory, filePath);

        // Create file if it does not exist
        if (!file.exists()) {
            try {
                Files.createFile(Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not create file");
            }
        }

        return file;
    }

    /**
     * @param file Files to overwrite
     * @param content Content to overwrite in file
     */
    public static void overwriteFile(File file, byte[] content) {
        // "false" means we overwrite the file
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
            // Write bytes to file
            fileOutputStream.write(content);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not overwrite to file");
        }
    }

    /**
     * @param directory Directory in which file is
     * @param filename Name of file to delete
     */
    public static void deleteFile(File directory, String filename) {
        File file = getOrCreateFile(directory, filename);
        deleteFile(file);
    }

    /**
     * @param file File to delete
     */
    public static void deleteFile(File file) {
        boolean result = file.delete();

        if (!result) {
            throw new IllegalArgumentException(String.format("Could not delete file %s", file.getPath()));
        }
    }
}
