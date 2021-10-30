package ca.ulaval.glo3100.utils;

import java.io.File;

public class FileUtils {

    /**
     * @param filename Filename of file
     * @return existing or newly created file
     */
    public static File getOrCreateFile(String filename) {
        return new File(filename);
    }
}
