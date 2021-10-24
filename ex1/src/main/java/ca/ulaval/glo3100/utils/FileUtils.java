package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.args.FileType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
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

    /**
     * @param files Files to convert to bytes
     * @return List of bytes for each file (filename and content)
     */
    public static List<FileAsBytes> toBytes(List<File> files) {
        List<FileAsBytes> fileAsBytes = new ArrayList<>();

        for (File file : files) {
            byte[] filename = file.getName().getBytes();
            byte[] content;

            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("File %s could not be converted to bytes.", file.getPath()));
            }

            fileAsBytes.add(new FileAsBytes(filename, content));
        }

        return fileAsBytes;
    }

    /**
     * @param directory Directory to create new file
     * @param filename Filename of new file
     * @return newly created file
     */
    public static File createFile(File directory, String filename) {
        if (directory.isDirectory()) {
            return new File(directory, filename);
        } else {
            throw new IllegalArgumentException("Provided directory is not a directory");
        }
    }

    /**
     * @param files Files to delete
     */
    public static File deleteFiles(List<File> files) {
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
     * @param fileAsStrings List of file as strings (one line = filename, next line = content)
     */
    public static void saveStrings(File directory, String filename, List<FileAsStrings> fileAsStrings) {
        File file = createFile(directory, filename);
        FileWriter fileWriter;

        try {
            // Open new file writer
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("FileWrite could not be instantiated with given directory and filename");
        }

        for (int i = 0; i < fileAsStrings.size(); i++) {
            try {
                fileWriter.write(fileAsStrings.get(i).filename);
                fileWriter.write("\n");
                fileWriter.write(fileAsStrings.get(i).content);
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
}
