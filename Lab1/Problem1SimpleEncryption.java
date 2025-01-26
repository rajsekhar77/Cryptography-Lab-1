package Cryptography.Lab1;

public class Problem1SimpleEncryption {
    public static String encrypt(String str, int k) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = (char) ('a' + (str.charAt(i) - 'a' + k) % 26);
            sb.append(c);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String plainText = "computerscienceengineeringsrmuniversity";
        int key = 4;
        String Encrypted = encrypt(plainText, key);

        System.out.println("Encrypted Message: " + Encrypted);
    }
}
