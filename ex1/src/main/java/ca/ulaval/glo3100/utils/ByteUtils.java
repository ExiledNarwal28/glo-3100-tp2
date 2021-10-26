package ca.ulaval.glo3100.utils;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ByteUtils {

    /**
     * @param listOfBytes List of bytes to convert
     * @return List of bytes converted to list of base 64 strings
     */
    public static List<String> toStrings(List<byte[]> listOfBytes) {
        return listOfBytes.stream().map(bytes -> Base64.getEncoder().encodeToString(bytes)).collect(Collectors.toList());
    }

    /**
     * @param listOfStrings List of strings to convert
     * @return List of strings converted to list of bytes
     */
    public static List<byte[]> toBytes(List<String> listOfStrings) {
        return listOfStrings.stream().map(string -> Base64.getDecoder().decode(string)).collect(Collectors.toList());
    }
}
