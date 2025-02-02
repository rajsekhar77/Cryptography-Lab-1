package Task2;

import Lab2.Q2DESLibrary;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.*;
import java.util.Base64;

public class ClientSideDES {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to the server.");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            SecretKey secretKey = Q2DESLibrary.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            // Sample plaintext
            String plainText = "RajaSekhar!";
            // Encrypt
            String encryptedText = Q2DESLibrary.encrypt(plainText, secretKey);
            output.writeUTF(encryptedText);
            output.writeUTF(encodedKey);
            System.out.println("Original Message sent to server: " + plainText);
            System.out.println("Cipher Message sent to server: " + encryptedText);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
