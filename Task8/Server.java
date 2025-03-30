package Task8;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;

import static Task8.SHA512_Implementation.SHA512Generator;

public class Server {
    // Private key of server
    private static final BigInteger y = new BigInteger(1024, new Random());

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            BigInteger g = new BigInteger("23");  // Primitive root
            BigInteger n = new BigInteger("563"); // Prime number

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
            System.out.println("Shared Secret Key: " + Key);

            // Convert shared key to AES key
            String AESKey = ConvertToAESKey(Key);

            // Receiving encrypted message from client
            String EncryptedMsg = input.readUTF();
            System.out.println("Received Encrypted Message: " + EncryptedMsg);

            // Decrypt message
            String DecryptedMsg = DecryptAES(EncryptedMsg, AESKey);
            System.out.println("Decrypted Message: " + DecryptedMsg);

            // Split message to extract actual text and hash
            String[] parts = DecryptedMsg.split("\\*\\*\\*", 2);
            if (parts.length != 2) {
                output.writeUTF("ERROR: Message format invalid.");
                return;
            }

            String receivedMessage = parts[0];
            String receivedHash = parts[1];

            // Compute SHA-512 hash of received message
            String computedHash = SHA512Generator(receivedMessage);

            // Verify integrity
            if (computedHash.equals(receivedHash)) {
                output.writeUTF("INTEGRITY VERIFIED: Message is authentic.");
                System.out.println("Integrity Check PASSED!");
            } else {
                output.writeUTF("ERROR: Integrity check failed.");
                System.out.println("Integrity Check FAILED!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Convert BigInteger Key to AES Key (128-bit)
    public static String ConvertToAESKey(BigInteger key) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.toByteArray());
        byte[] aesKey = new byte[16]; // 128-bit key
        System.arraycopy(keyBytes, 0, aesKey, 0, 16);
        return Base64.getEncoder().encodeToString(aesKey);
    }

    // AES Decryption Function
    public static String DecryptAES(String encryptedText, String AESKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(AESKey);
        SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
