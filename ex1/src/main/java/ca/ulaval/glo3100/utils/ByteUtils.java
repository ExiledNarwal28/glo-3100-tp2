package ca.ulaval.glo3100.utils;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ByteUtils {

    /**
     * @param bytes List of file as bytes bytes to convert
     * @return List of file as bytes converted to list of file as base 64 strings
     */
    public static List<FileAsStrings> toStrings(List<FileAsBytes> bytes) {
        return bytes.stream().map(fileAsBytes -> new FileAsStrings(
                Base64.getEncoder().encodeToString(fileAsBytes.filename),
                Base64.getEncoder().encodeToString(fileAsBytes.content)
        )).collect(Collectors.toList());
    }
}
