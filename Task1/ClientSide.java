package Task1;

import Lab1.Problem7PlayFairEncryption;
import java.io.*;
import java.net.*;

public class ClientSide {
    public static String encrypt(String plainText, String key) {
        StringBuilder cipherText = new StringBuilder();
        plainText = plainText.toUpperCase();
        key = key.toUpperCase();
        int keyIndex = 0;
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                char k = key.charAt(keyIndex);
                int encryptedChar = ((c - 'A') + (k - 'A')) % 26 + 'A';
                cipherText.append((char) encryptedChar);
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                cipherText.append(c);
            }
        }
        return cipherText.toString();
    }
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to the server.");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            String original = "wearediscoveredsaveyourself";
            String plainText = original;
            plainText = plainText.toLowerCase().replace('j','i').replace(" ","");
            String key = "deceptive";
            String encrypted = encrypt(original, key);
            char[][] keyMatrix = {
                    {'s','r','m','a','p'},
                    {'u','n','i','v','e'},
                    {'t','y','b','c','d'},
                    {'f','g','h','k','l'},
                    {'o','q','w','x','z'}
            };
            String playfairEncrypted = Problem7PlayFairEncryption.PlayfairEncrypt(plainText,keyMatrix);
            output.writeUTF(encrypted);
            output.writeUTF(playfairEncrypted);
            System.out.println("Original Message sent to server: " + original);
            System.out.println("Cipher Message sent to server: " + encrypted);
            System.out.println("Playfair Encrypted Message sent to server: " + playfairEncrypted);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
