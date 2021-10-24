package ca.ulaval.glo3100.utils;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class ByteUtils {

    /**
     * @param bytes List of bytes to convert
     * @return List of bytes converted to list of base 64 strings
     */
    public static List<String> toStrings(List<byte[]> bytes) {
        return bytes.stream().map(Base64.getEncoder()::encodeToString).collect(Collectors.toList());
    }
}
