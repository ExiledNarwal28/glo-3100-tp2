package ca.ulaval.glo3100.utils;

import ca.ulaval.glo3100.args.FileType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TextFileUtils {

    /**
     * @param directory Directory to create new file
     * @param filename Filename of new file
     * @param fileTypes List of file types
     */
    public static void saveFileTypes(File directory, String filename, List<FileType> fileTypes) {
        File file = FileUtils.getOrCreateFile(directory, filename);
        FileWriter fileWriter;

        try {
            // Open new file writer
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("FileWrite could not be instantiated with given directory and filename");
        }

        // For each file type
        for (int i = 0; i < fileTypes.size(); i++) {
            try {
                // Write file type in given file
                fileWriter.write(fileTypes.get(i).toString());
            } catch (IOException e) {
                throw new IllegalArgumentException("Could not write to file");
            }

            // Add newline unless it's the last string
            if (i < fileTypes.size() - 1) {
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
     * @param directory Directory to search for file in
     * @param filename Name of file containing each file type
     * @return Saved file types for given directory and filename
     */
    public static List<FileType> getFileTypes(File directory, String filename) {
        File file = FileUtils.getOrCreateFile(directory, filename);

        // Get lines in file
        List<String> lines;
        try {
            lines = Files.lines(Paths.get(file.getPath())).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read file");
        }

        return lines.stream().map(FileType::get).collect(Collectors.toList());
    }
}
