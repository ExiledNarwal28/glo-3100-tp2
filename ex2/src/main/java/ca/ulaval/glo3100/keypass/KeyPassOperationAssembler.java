package ca.ulaval.glo3100.keypass;

import ca.ulaval.glo3100.console.Logger;

public class KeyPassOperationAssembler {

    private static final String ADD_OPERATION_ARG = "-a";
    private static final String LIST_OPERATION_ARG = "-l";
    private static final String DECRYPT_OPERATION_ARG = "-d";
    private static final String URL_ARG = "-url";
    private static final String USER_ARG = "-user";
    private static final String PASSWORD_ARG = "-pwd";
    private static final String INDEX_ARG = "-i";
    private static final String DEBUG_ARG = "-debug";

    /**
     * @param args args sent to program
     * @return newly assembled key pass operation
     */
    public static KeyPassOperation assemble(String[] args) {
        // Default values
        String mainPassword = null;
        KeyPassOperationType operationType = null;
        KeyPassOperation operation = null;
        String url = null;
        String user = null;
        String password = null;
        boolean decryptUser = false;
        boolean decryptPassword = false;
        int index = 0;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case ADD_OPERATION_ARG:
                    operationType = KeyPassOperationType.ADD;
                    mainPassword = args[i + 1];
                    break;
                case LIST_OPERATION_ARG:
                    operationType = KeyPassOperationType.LIST;
                    mainPassword = args[i + 1];
                    break;
                case DECRYPT_OPERATION_ARG:
                    operationType = KeyPassOperationType.DECRYPT;
                    mainPassword = args[i + 1];
                    break;
                case URL_ARG:
                    url = args[i + 1];
                    break;
                case USER_ARG:
                    if (operationType != null) {
                        switch(operationType) {
                            case ADD:
                                user = args[i + 1];
                                break;
                            case DECRYPT:
                                decryptUser = true;
                                break;
                        }
                    }
                    break;
                case PASSWORD_ARG:
                    if (operationType != null) {
                        switch(operationType) {
                            case ADD:
                                password = args[i + 1];
                                break;
                            case DECRYPT:
                                decryptPassword = true;
                                break;
                        }
                    }
                    break;
                case INDEX_ARG:
                    index = Integer.parseInt(args[i + 1]);
                    break;
                case DEBUG_ARG:
                    Logger.isDebugging = true;
                    break;
            }
        }

        if (mainPassword == null) {
            throw new IllegalArgumentException("Provide a main password after -a (add) -l (list) or -d (decrypt)");
        }

        switch(operationType) {
            case ADD:
                return assembleAddOperation(mainPassword, url, user, password);
            case LIST:
                return assembleListOperation(mainPassword);
            case DECRYPT:
                return assembleDecryptOperation(mainPassword, index, decryptUser, decryptPassword);
            default:
                throw new IllegalArgumentException("Provide operation with -a (add) -l (list) or -d (decrypt)");
        }
    }

    /**
     * @param mainPassword main password of key pass
     * @param url url to add
     * @param user user to add
     * @param password password to add
     * @return newly assembled add key pass operation
     */
    private static KeyPassOperation assembleAddOperation(String mainPassword, String url, String user, String password) {
        if (url == null) {
            throw new IllegalArgumentException("Provide a url with -url");
        }

        if (user == null) {
            throw new IllegalArgumentException("Provide a user with -user");
        }

        if (password == null) {
            throw new IllegalArgumentException("Provide a password with -pwd");
        }

        return new AddKeyPassOperation(mainPassword, url, user, password);
    }

    /**
     * @param mainPassword main password of key pass
     * @return newly assembled list key pass operation
     */
    private static KeyPassOperation assembleListOperation(String mainPassword) {
        return new ListKeyPassOperation(mainPassword);
    }

    /**
     * @param mainPassword main password of key pass
     * @param index index of entry to decrypt
     * @param decryptUser true if we want to decrypt user
     * @param decryptPassword true if we want to decrypt password
     * @return newly assembled decrypt key pass operation
     */
    private static KeyPassOperation assembleDecryptOperation(String mainPassword, int index, boolean decryptUser, boolean decryptPassword) {
        if (index == 0) {
            throw new IllegalArgumentException("Provide an index with -i");
        }

        return new DecryptKeyPassOperation(mainPassword, index, decryptUser, decryptPassword);
    }
}