package ca.ulaval.glo3100.utils;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ByteUtils {

    /**
     * @param filesAsBytes List of file as bytes to convert
     * @return List of file as bytes converted to list of file as base 64 strings
     */
    public static List<FileAsStrings> toStrings(List<FileAsBytes> filesAsBytes) {
        return filesAsBytes.stream().map(fileAsBytes -> new FileAsStrings(
                Base64.getEncoder().encodeToString(fileAsBytes.filename),
                Base64.getEncoder().encodeToString(fileAsBytes.content)
        )).collect(Collectors.toList());
    }

    /**
     * @param filesAsStrings List of file as strings to convert
     * @return List of file as strings converted to list of file as bytes
     */
    public static List<FileAsBytes> toBytes(List<FileAsStrings> filesAsStrings) {
        return filesAsStrings.stream().map(fileAsStrings -> new FileAsBytes(
                Base64.getDecoder().decode(fileAsStrings.filename),
                Base64.getDecoder().decode(fileAsStrings.content)
        )).collect(Collectors.toList());
    }
}
