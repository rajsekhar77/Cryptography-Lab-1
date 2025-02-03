package Task3;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class ServerSideAES {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                // Receive encrypted message and key
                String encryptedText = input.readUTF();
                String encodedKey = input.readUTF();

                // Decode key
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

                // Decrypt message
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
                String decryptedMessage = new String(decryptedBytes);
                System.out.println("Cipher Message from client: " + encryptedText);
                System.out.println("Decrypted Message from client: " + decryptedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
