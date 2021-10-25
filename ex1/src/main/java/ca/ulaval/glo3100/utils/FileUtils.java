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
import java.util.stream.Stream;

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
    public static List<FileAsBytes> toBytes(List<File> files) {
        List<FileAsBytes> fileAsBytes = new ArrayList<>();

        for (File file : files) {
            byte[] filename = file.getName().getBytes(StandardCharsets.UTF_8);
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
     * @param fileAsStrings List of file as strings (one line = filename, next line = content)
     */
    public static void saveFilesAsStrings(File directory, String filename, List<FileAsStrings> fileAsStrings) {
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

    // TODO : Add javadoc
    public static void saveFilesAsBytes(File directory, List<FileAsBytes> filesAsBytes) {
        for (FileAsBytes decryptedFilesAsByte : filesAsBytes) {
            String filename = Base64.getEncoder().encodeToString(decryptedFilesAsByte.filename);
            File file = getOrCreateFile(directory, filename);

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                // Write bytes to file
                fileOutputStream.write(decryptedFilesAsByte.content);
            } catch (IOException ioe) {
                throw new IllegalArgumentException("Could not write file");
            }
        }
    }

    // TODO : Add javadoc
    public static List<FileAsStrings> getFilesAsStrings(File directory, String filename) {
        File file = getOrCreateFile(directory, filename);

        // Get lines in file
        List<String> lines;
        try {
            lines = Files.lines(Paths.get(file.getPath())).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file");
        }

        // Get encrypted filename and content
        List<FileAsStrings> fileAsStrings = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 2) {
            fileAsStrings.add(new FileAsStrings(lines.get(i), lines.get(i + 1)));
        }

        return fileAsStrings;
    }
}
