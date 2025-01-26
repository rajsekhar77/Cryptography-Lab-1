package Cryptography.Lab1;

import java.util.HashMap;

public class Problem5MonoAlphabeticDecryption {
    public static void main(String[] args) {

        String key = "ANDREWICKSOHTBFGJLMPQUVXYZ";

        HashMap<Character, Character> map = new HashMap<>();

        for(int i = 0; i < key.length(); i++) {
            map.put((char)('A'+i),key.charAt(i));
        }

        String Encrypted = "SEEMSEAOMEDSAMHL";

        StringBuffer sb = new StringBuffer();

       for(int i = 0; i < Encrypted.length(); i++) {
           sb.append(map.get(Encrypted.charAt(i)));
       }

       System.out.println("Decrypted Text : " + sb.toString());


    }
}
