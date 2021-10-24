package ca.ulaval.glo3100.console;

public class Logger {

    public static boolean isDebugging = false;

    public static void logInfo(String message) {
        System.out.println(message);
    }

    public static void logDebug(String message) {
        if (isDebugging) {
            System.out.println(message);
        }
    }
}
