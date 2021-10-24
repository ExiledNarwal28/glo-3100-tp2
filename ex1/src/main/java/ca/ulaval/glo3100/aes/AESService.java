package ca.ulaval.glo3100.aes;

import ca.ulaval.glo3100.args.Args;
import ca.ulaval.glo3100.args.FileType;
import ca.ulaval.glo3100.console.Logger;

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

    public static void execute(Args args) {
        switch (args.operation) {
            case ENCRYPT:
                encrypt(args.directory, args.fileTypes);
            case DECRYPT:
                decrypt(args.directory);
        }
    }

    // TODO : Add javadocs
    private static void encrypt(File directory, List<FileType> fileTypes) {
        Logger.logDebug(String.format(
                "Encrypting with directory %s and file types %s",
                directory,
                fileTypes.stream().map(FileType::toString).collect(Collectors.joining(", "))));

        // TODO : Encrypt
    }

    // TODO : Add javadocs
    private static void decrypt(File directory) {
        Logger.logDebug(String.format("Decrypting with directory %s", directory));

        // TODO : Decrypt
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
