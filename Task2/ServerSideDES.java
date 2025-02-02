package Task2;

import Lab2.Q2DESLibrary;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class ServerSideDES {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is running and waiting for a connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");
            DataInputStream input = new DataInputStream(socket.getInputStream());
            String receivedMessage = input.readUTF();
            String encodedkey = input.readUTF();
            byte[] decodedKey = Base64.getDecoder().decode(encodedkey);
            SecretKey secretKey = new SecretKeySpec(decodedKey, "DES");
            System.out.println("CIPHER DES from client: " + receivedMessage);
            String decrypteddes= Q2DESLibrary.decrypt(receivedMessage, secretKey);
            System.out.println("Hello, client! Your DES message was: " + decrypteddes);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
