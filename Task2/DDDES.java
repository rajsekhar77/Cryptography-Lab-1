package Task2;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.util.Base64;

public class DDDES {
    private static final String TRIPLE_DES_ALGORITHM = "DESede";

    public static SecretKey generateKey(String key) throws Exception {
        DESedeKeySpec keySpec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(TRIPLE_DES_ALGORITHM);
        return keyFactory.generateSecret(keySpec);
    }

    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRIPLE_DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRIPLE_DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
    public static void main(String[] args) {
        try {
            String key = "ThisIsASecretKeyFor3DES!"; // 24-byte key
            String plaintext = "Hello, Triple DES!";

            SecretKey secretKey = generateKey(key);
            String encryptedText = encrypt(plaintext, secretKey);
            String decryptedText = decrypt(encryptedText, secretKey);

            System.out.println("Original Text: " + plaintext);
            System.out.println("Encrypted Text: " + encryptedText);
            System.out.println("Decrypted Text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
