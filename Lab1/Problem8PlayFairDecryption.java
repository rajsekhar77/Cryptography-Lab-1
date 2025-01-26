package Cryptography.Lab1;

public class Problem8PlayFairDecryption {
    public static void main(String[] args) {
        String Encrypted = "LIIUDLTQNSLIZETQVTPKZEZFVBVZ".toLowerCase();

        char[][] keyMatrix = {
                { 's', 'r', 'm', 'a', 'p' },
                { 'u', 'n', 'i', 'v', 'e' },
                { 't', 'y', 'b', 'c', 'd' },
                { 'f', 'g', 'h', 'k', 'l' },
                { 'o', 'q', 'w', 'x', 'z' }
        };

        String Decrypted = decrypt(Encrypted, keyMatrix);
        System.out.println("Decrypted Message : " + Decrypted);
    }

    public static String decrypt(String et, char[][] keyMatrix) {

        StringBuilder dt = new StringBuilder();

        for (int i = 0; i < et.length(); i += 2) {
            int[] fc = findIndex(et.charAt(i), keyMatrix);
            int[] sc = findIndex(et.charAt(i + 1), keyMatrix);

            if (fc[0] == sc[0]) { // same row

                dt.append(keyMatrix[fc[0]][(fc[1] + 4) % 5]).append(keyMatrix[sc[0]][(sc[1] + 4) % 5]);

            } else if (fc[1] == sc[1]) { // same col

                dt.append(keyMatrix[(fc[0] + 4) % 5][fc[1]]).append(keyMatrix[(sc[0] + 4) % 5][sc[1]]);

            } else { // rectangle rule

                dt.append(keyMatrix[fc[0]][sc[1]]).append(keyMatrix[sc[0]][fc[1]]);

            }
        }

        return dt.toString();
    }

    public static int[] findIndex(char c, char[][] keyMatrix) {
        for (int i = 0; i < keyMatrix.length; i++) {
            for (int j = 0; j < keyMatrix[i].length; j++) {
                if (c == keyMatrix[i][j]) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }
}
