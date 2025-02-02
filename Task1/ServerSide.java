package Task1;
import Lab1.Problem8PlayFairDecryption;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    public static String decrypt(String cipherText, String key) {
        StringBuilder plainText = new StringBuilder();
        cipherText = cipherText.toUpperCase();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                char k = key.charAt(keyIndex);
                int decryptedChar = ((c - k + 26) % 26) + 'A';
                plainText.append((char) decryptedChar);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                plainText.append(c);
            }
        }
        return plainText.toString();
    }
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is running and waiting for a connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");
            DataInputStream input = new DataInputStream(socket.getInputStream());
            String receivedMessage = input.readUTF();
            System.out.println("CIPHER from client: " + receivedMessage);
            String receivedMessagePlayfair = input.readUTF();
            System.out.println("Playfair from client: " + receivedMessagePlayfair);
            String key = "deceptive";
            String decryptedMessage = decrypt(receivedMessage, key);
            System.out.println("Hello, client! Your ceaser message was: " + decryptedMessage);
            char[][] keyMatrix = {
                    {'s','r','m','a','p'},
                    {'u','n','i','v','e'},
                    {'t','y','b','c','d'},
                    {'f','g','h','k','l'},
                    {'o','q','w','x','z'}
            };
            String playfairDecrypted =
                    Problem8PlayFairDecryption.decryptPlayfair(receivedMessagePlayfair,keyMatrix);
            System.out.println("Hello, client! Your Playfair message was: " + playfairDecrypted);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
