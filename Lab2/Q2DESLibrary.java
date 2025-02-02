package Lab2;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Q2DESLibrary {
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
    public static void main(String[] args) throws Exception {
        // Generate DES key
        SecretKey secretKey = generateKey();
        // Sample plaintext
        String plainText = "RajaSekhar!";
        // Encrypt
        String encryptedText = encrypt(plainText, secretKey);
        System.out.println("Encrypted: " + encryptedText);
        // Decrypt
        String decryptedText = decrypt(encryptedText, secretKey);
        System.out.println("Decrypted: " + decryptedText);
    }

}
