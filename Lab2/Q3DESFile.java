package Lab2;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.util.Base64;

public class Q3DESFile {
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        return keyGen.generateKey();
    }
    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
    public static void writeFile(String fileName, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }
    public static String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }
    public static void main(String[] args) throws Exception {
        // Generate DES key
        SecretKey secretKey = generateKey();
        // File paths
        String originalFile = "original.txt";
        String encryptedFile = "encrypted.txt";
        String decryptedFile = "decrypted.txt";
        // Write sample text to original file
        String plainText = "Hello, this is Raja Sekhar!";
        writeFile(originalFile, plainText);
        // Encrypt file content and save to new file
        String fileContent = readFile(originalFile);
        String encryptedText = encrypt(fileContent, secretKey);
        writeFile(encryptedFile, encryptedText);
        // Decrypt file content and save to new file
        String encryptedFileContent = readFile(encryptedFile);
        String decryptedText = decrypt(encryptedFileContent, secretKey);
        writeFile(decryptedFile, decryptedText);
        System.out.println("Encryption and Decryption completed. Check the files.");
    }

}
