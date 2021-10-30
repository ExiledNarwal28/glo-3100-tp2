package ca.ulaval.glo3100.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ByteUtils {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * @param bytes Bytes to convert
     * @return Bytes converted to base 64 strings
     */
    public static String toString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * @param string Strings to convert
     * @return String converted to bytes
     */
    public static byte[] toBytes(String string) {
        return string.getBytes(CHARSET);
    }
}
