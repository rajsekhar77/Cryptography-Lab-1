package Cryptography.Lab1;

import java.util.HashMap;

public class Problem4MonoAlphabeticEncryption {
    public static void main(String[] args) {

        String key = "ANDREWICKSOHTBFGJLMPQUVXYZ";

        HashMap<Character, Character> map = new HashMap<>();

        for (int i = 0; i < key.length(); i++) {
            map.put((char) ('A' + i), key.charAt(i));
        }

        String PlainText = "wewishtoreplaceplayer";
        PlainText = PlainText.toUpperCase();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < PlainText.length(); i++) {
            sb.append(map.get(PlainText.charAt(i)));
        }
        System.out.println("Encrypted Text : " + sb.toString());
    }
}
