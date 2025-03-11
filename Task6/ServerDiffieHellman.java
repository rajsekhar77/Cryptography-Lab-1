package Task6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ServerDiffieHellman {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            BigInteger g = new BigInteger("23");  // Primitive root
            BigInteger n = new BigInteger("563"); // Prime number

            // Private key of server
            BigInteger y = new BigInteger(1024, new Random());

            // Calculating server's public key (K2)
            BigInteger K2 = g.modPow(y, n);

            // Receiving client's public key (K1)
            String K1 = input.readUTF();
            BigInteger AK1 = new BigInteger(K1);
            System.out.println("Public K1 received from Client: " + AK1);

            // Sending server's public key (K2) to client
            output.writeUTF(String.valueOf(K2));
            System.out.println("Public K2 sent to Client: " + K2);

            // Calculating shared secret key
            BigInteger Key = AK1.modPow(y, n);
            System.out.println("Shared Secret Key (Server): " + Key);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}