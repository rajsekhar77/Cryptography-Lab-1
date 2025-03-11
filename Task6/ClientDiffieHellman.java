package Task6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Random;

public class ClientDiffieHellman {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to the server.");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            BigInteger g = new BigInteger("23");  // Primitive root
            BigInteger n = new BigInteger("563"); // Prime number

            // Private key of client
            BigInteger x = new BigInteger(1024, new Random());

            // Calculating client's public key (K1)
            BigInteger K1 = g.modPow(x, n);
            output.writeUTF(String.valueOf(K1));
            System.out.println("Public K1 sent to Server: " + K1);

            // Receiving server's public key (K2)
            String K2 = input.readUTF();
            BigInteger BK2 = new BigInteger(K2);
            System.out.println("Public K2 received from Server: " + BK2);

            // Calculating shared secret key
            BigInteger Key = BK2.modPow(x, n);
            System.out.println("Shared Secret Key (Client): " + Key);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}