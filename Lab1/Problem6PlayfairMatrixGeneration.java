package Cryptography.Lab1;

import java.util.LinkedHashSet;

public class Problem6PlayfairMatrixGeneration {
    public static void main(String[] args) {

        String key = "srmapuniversity";
        key = key.replace('j', 'i');

        int row = 5, col = 5;

        char[][] matrix = new char[row][col];


        LinkedHashSet<Character> set = new LinkedHashSet<>();
        LinkedHashSet<Character> alpha = new LinkedHashSet<>();


        for (int i=0;i<key.length();i++) {
             set.add(key.charAt(i));
        }

        System.out.println(set);


        for (int i=0;i<26;i++) {
            if (i != 9) {
                alpha.add((char) ('a' + i));
            }
        }
        System.out.println(alpha);

        // Fill the matrix with key characters first
        int a = 0, b = 0;
        for (char c : set) {
            matrix[a][b] = c;
            alpha.remove(c); // Remove used characters from the alphabet
            b++;
            if (b == col) {
                b = 0;
                a++;
            }
        }

        // Fill the matrix with remaining alphabet characters
        for (char c : alpha) {
            matrix[a][b] = c;
            b++;
            if (b == col) {
                b = 0;
                a++;
            }
        }

        // Print the Playfair cipher matrix
        System.out.println("Playfair Key Matrix:");
        for (a = 0; a < row; a++) {
            for (b = 0; b < col; b++) {
                System.out.print(matrix[a][b] + " ");
            }
            System.out.println();
        }
    }
}
