package Task8;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Random;
import static Task8.SHA512_Implementation.SHA512Generator;

public class Client {
    private static final BigInteger x = new BigInteger(1024, new Random());

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to the server.");
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            BigInteger g = new BigInteger("23");  // Primitive root
            BigInteger n = new BigInteger("563"); // Prime number

            BigInteger K1 = g.modPow(x, n);
            output.writeUTF(String.valueOf(K1));
            System.out.println("Public K1 sent to Server: " + K1);

            // Receiving server's public key (K2)
            String K2 = input.readUTF();
            BigInteger BK2 = new BigInteger(K2);
            System.out.println("Public K2 received from Server: " + BK2);

            // Calculating shared secret key
            BigInteger Key = BK2.modPow(x, n);
            System.out.println("Shared Secret Key: " + Key);

            // Convert shared key to AES key (128-bit)
            String AESKey = ConvertToAESKey(Key);

            // Original Message
            String Message = "Im in Danger!";
            String PlainText = Message + "***" + SHA512Generator(Message);

            // Encrypt Message using shared key
            String Encrypted = EncryptAES(PlainText, AESKey);

            // Send Encrypted Message to Server
            output.writeUTF(Encrypted);
            System.out.println("Sent Encrypted to Server: " + Encrypted);

        } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // Convert BigInteger Key to AES Key (128-bit)
    public static String ConvertToAESKey (BigInteger key) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.toByteArray());
        byte[] aesKey = new byte[16]; // 128-bit key
        System.arraycopy(keyBytes, 0, aesKey, 0, 16);
        return Base64.getEncoder().encodeToString(aesKey);
    }

    // AES Encryption Function
    public static String EncryptAES (String plainText, String AESKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(AESKey);
        SecretKey secretKey = new SecretKeySpec(decodedKey, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
