package Task6;

import java.math.BigInteger;

public class Arithematic {
    public static void main(String[] args) {
        BigInteger a = new BigInteger("182841384165841685416854134135");
        BigInteger b = new BigInteger("135481653441354138548413384135");

        BigInteger c = a.add(b);
        BigInteger d = a.subtract(b);
        BigInteger e = a.multiply(b);
        BigInteger f = a.divide(b);
        BigInteger g = a.mod(b);
//        BigInteger h = a.modInverse(b);

        System.out.println("Performing Addition Operation of a and b " + c);
        System.out.println("Performing Subtraction Operation of a and b " + d);
        System.out.println("Performing Multiply Operation of a and b " + e);
        System.out.println("Performing Division Operation of a and b " + f);
        System.out.println("Performing Modulus Operation of a and  b " + g);
//        System.out.println("Performing Modulus Operation of a and  b" + h);

    }
}
