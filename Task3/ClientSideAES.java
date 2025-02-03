package Task3;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class ClientSideAES {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to the server.");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            // Generate AES Key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            // Sample plaintext
            String plainText = "RajaSekhar!";

            // Encrypt message
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

            // Send encrypted message and key
            output.writeUTF(encryptedText);
            output.writeUTF(encodedKey);
            System.out.println("Original Message sent to server: " + plainText);
            System.out.println("Cipher Message sent to server: " + encryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
