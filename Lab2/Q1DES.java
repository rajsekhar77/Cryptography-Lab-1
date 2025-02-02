package Lab2;

public class Q1DES {
    // Keeping your existing arrays and box definitions
    private static final int[] PERM_ARRAY =
            {15,6,19,20,28,11,27,16,0,14,22,25,4,17,30,9,1,7,23,13,31,26,2,8,18,12,29,5,21,10,3,24};
    private static final String[][] SBOX = {
            {"0010", "0100", "0010", "1001", "1011", "1111", "1011", "1111", "1111", "1010", "1010",
                    "0100", "0110", "1101", "0110", "0001"},
            {"0010", "0100", "1001", "1001", "1011", "1111", "1011", "0010", "1011", "1111", "1111",
                    "1011", "1010", "0100", "0110", "0001"},
            {"0010", "0100", "1001", "1001", "1011", "1111", "1011", "0010", "1011", "1111", "1111",
                    "1011", "1010", "0100", "0110", "0001"},
            {"0010", "0100", "1001", "1001", "1011", "1111", "1011", "0010", "1011", "1111", "1111",
                    "1011", "1010", "0100", "0110", "0001"}
    };
    private static final int[] EXP_ARRAY = {
            31, 0, 1, 2, 3, 4, 3, 4,
            5, 6, 7, 8, 7, 8, 9, 10,
            11, 12, 11, 12, 13, 14, 15, 16,
            15, 16, 17, 18, 19, 20, 19, 20,
            21, 22, 23, 24, 23, 24, 25, 26,
            27, 28, 27, 28, 29, 30, 31, 0
    };
    private static final int[] PC1_ARRAY = {
            56, 48, 40, 32, 24, 16, 8,
            0, 57, 49, 41, 33, 25, 17,
            9, 1, 58, 50, 42, 34, 26,
            18, 10, 2, 59, 51, 43, 35,
            62, 54, 46, 38, 30, 22, 14,
            6, 61, 53, 45, 37, 29, 21,
            13, 5, 60, 52, 44, 36, 28,
            20, 12, 4, 27, 19, 11, 3
    };
    private static final int[] PC2_ARRAY = {
            13, 16, 10, 23, 0, 4,
            2, 27, 14, 5, 20, 9,
            22, 18, 11, 3, 25, 7,
            15, 6, 26, 19, 12, 1,
            40, 51, 30, 36, 46, 54,
            29, 39, 50, 44, 32, 47,
            43, 48, 38, 55, 33, 52,
            45, 41, 49, 35, 28, 31
    };
    // Key generation for all rounds
    private static String[] generateRoundKeys(String key) {
        String[] roundKeys = new String[16];
        String pc1Key = PC1(key);
        String leftKey = pc1Key.substring(0, 28);
        String rightKey = pc1Key.substring(28, 56);
        for (int round = 0; round < 16; round++) {
            // Number of left shifts for each round
            int shifts = (round == 0 || round == 1 || round == 8 || round == 15) ? 1 : 2;
            for (int i = 0; i < shifts; i++) {
                leftKey = LeftCircularShift(leftKey);
                rightKey = LeftCircularShift(rightKey);
            }
            String combinedKey = leftKey + rightKey;
            roundKeys[round] = PC2(combinedKey);
        }
        return roundKeys;
    }
    // Encryption function
    public static String encrypt(String plainText, String key) {
        // Generate round keys
        String[] roundKeys = generateRoundKeys(key);
        // Initial text to binary conversion
        String binaryText = textToBinary(plainText);
        binaryText = binaryText.substring(0, 64); // Ensure 64-bit block
        String left = binaryText.substring(0, 32);
        String right = binaryText.substring(32, 64);
        // 16 rounds of encryption
        for (int round = 0; round < 16; round++) {
            String temp = right;
            right = XOR(left, feistelFunction(right, roundKeys[round]));
            left = temp;
        }
        // Final swap
        String cipherText = right + left;
        return binaryToText(cipherText);
    }
    // Decryption function
    public static String decrypt(String cipherText, String key) {
        // Generate round keys
        String[] roundKeys = generateRoundKeys(key);
        // Convert cipher text to binary
        String binaryText = textToBinary(cipherText);
        binaryText = binaryText.substring(0, 64); // Ensure 64-bit block
        String left = binaryText.substring(0, 32);
        String right = binaryText.substring(32, 64);
        // 16 rounds of decryption (using keys in reverse order)
        for (int round = 15; round >= 0; round--) {
            String temp = right;
            right = XOR(left, feistelFunction(right, roundKeys[round]));
            left = temp;
        }
        // Final swap
        String plainText = right + left;
        return binaryToText(plainText);
    }
    // Feistel function combining expansion, XOR, S-box, and permutation
    private static String feistelFunction(String right, String roundKey) {
        String expanded = ExpansionFx(right);
        String xored = XOR(expanded, roundKey);
        String sboxed = SBox(xored);
        return Permutate(sboxed);
    }
    // Your existing helper functions (keeping their implementation)
    private static String Permutate(String input) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < PERM_ARRAY.length; i++) {
            sb.append(input.charAt(PERM_ARRAY[i]));
        }
        return sb.toString();
    }
    private static String SBox(String input) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            String subInput = input.substring(i*6, (i+1)*6);
            char f = subInput.charAt(0);
            char l = subInput.charAt(subInput.length()-1);
            String r = "" + f + l;
            String c = subInput.substring(1, 5);
            int row = Integer.parseInt(r, 2);
            int col = Integer.parseInt(c, 2);
            sb.append(SBOX[row][col]);
        }
        return sb.toString();
    }
    private static String XOR(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < a.length(); i++) {
            sb.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return sb.toString();
    }
    private static String ExpansionFx(String input) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < EXP_ARRAY.length; i++) {
            sb.append(input.charAt(EXP_ARRAY[i]));
        }
        return sb.toString();
    }
    private static String PC2(String key) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < PC2_ARRAY.length; i++) {
            sb.append(key.charAt(PC2_ARRAY[i]));
        }
        return sb.toString();
    }
    private static String LeftCircularShift(String key) {
        return key.substring(1) + key.charAt(0);
    }
    private static String PC1(String key) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < PC1_ARRAY.length; i++) {
            sb.append(key.charAt(PC1_ARRAY[i]));
        }
        return sb.toString();
    }
    private static String textToBinary(String text) {
        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            binary.append(String.format("%08d", Integer.parseInt(Integer.toBinaryString(c))));
        }
        return binary.toString();
    }
    private static String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            int charCode = Integer.parseInt(binary.substring(i, i + 8), 2);
            text.append((char) charCode);
        }
        return text.toString();
    }
    public static void main(String[] args) {
        String plainText = "RajaSekhar!";
        String key = "Secretkeyisthesecret";
        // Ensure key is in correct binary format
        key = textToBinary(key).substring(0, 64);
        System.out.println("Original Text: " + plainText);
        // Encrypt
        String encrypted = encrypt(plainText, key);
        System.out.println("Encrypted Text: " + encrypted);
        // Decrypt
        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted Text: " + decrypted);
    }
}
