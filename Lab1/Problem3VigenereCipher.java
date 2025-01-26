package Cryptography.Lab1;

public class Problem3VigenereCipher {
    public static String encrypt(String str, String key) {
        StringBuilder sb = new StringBuilder();

        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = (char) (str.charAt(i) - 'a');
            char k = (char) (key.charAt(j) - 'a');
            sb.append((char) ('a' + (c + k) % 26));
            j = (j + 1) % key.length();
        }
        return sb.toString();

    }

    public static String decrypt(String str, String key) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = (char) (str.charAt(i) - 'a');
            char k = (char) (key.charAt(j) - 'a');
            sb.append((char) ('a' + (c - k + 26) % 26));
            j = (j + 1) % key.length();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String plainText = "wearediscoveredsaveyourself".toLowerCase();
        String key = "deceptive";

        String Encrypted = encrypt(plainText, key);
        String Decrypted = decrypt(Encrypted.toLowerCase(), key);

        System.out.println("Encrypted Message: " + Encrypted);
        System.out.println("Decrypted Message: " + Decrypted);
    }
}
