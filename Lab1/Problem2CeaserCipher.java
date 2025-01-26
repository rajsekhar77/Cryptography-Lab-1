package Cryptography.Lab1;

public class Problem2CeaserCipher {
    public static void decrypt(String m, int k) {
        for (int j = k; j <= 26; j++) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < m.length(); i++) {
                if (!(m.charAt(i) == ' ')) {
                    sb.append((char) ('A' + ((m.charAt(i) - 'A' - j + 26) % 26)));
                } else {
                    sb.append(" ");
                }
            }
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        String Message = "PHHW PH DIWHU WKH WRJD SDUWB";
        int initalKeay = 1;
        decrypt(Message, initalKeay); // Ans is MEET ME AFTER THE TOGA PARTY
    }
}