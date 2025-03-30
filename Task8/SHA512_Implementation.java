package Task8;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512_Implementation {

    public static String SHA512Generator(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into sign-um representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Ensure the hash is padded to 128 characters
            while (hashtext.length() < 128) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        String PlainText = "Raja Sekhar";
        System.out.println("HashCode using SHA512 : "+SHA512Generator(PlainText));
        System.out.println("HashCode using SHA512 : "+SHA512Generator(PlainText).length());
    }
}
