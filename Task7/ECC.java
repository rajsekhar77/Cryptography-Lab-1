package Task7;

import java.math.BigInteger;
import java.security.SecureRandom;
public class ECC {

    private static final BigInteger P = new
            BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16); // Prime field
    private static final BigInteger A = BigInteger.ZERO; // Curve coefficient 'a'
    private static final BigInteger B = new BigInteger("7"); // Curve coefficient 'b'
    private static final BigInteger Gx = new
            BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16); // Base point x
    private static final BigInteger Gy = new
            BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16); // Base point y
    private static final BigInteger N = new
            BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16); // Order of G
            // Point class to represent ECC points
    static class Point {
        BigInteger x, y;
        Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }
    // Point addition on the elliptic curve
    private static Point pointAdd(Point p1, Point p2) {
        if (p1.x.equals(BigInteger.ZERO) && p1.y.equals(BigInteger.ZERO)) return p2;
        if (p2.x.equals(BigInteger.ZERO) && p2.y.equals(BigInteger.ZERO)) return p1;
        BigInteger lambda = p2.y.subtract(p1.y).multiply(p2.x.subtract(p1.x).modInverse(P)).mod(P);
        BigInteger xr = lambda.pow(2).subtract(p1.x).subtract(p2.x).mod(P);
        BigInteger yr = lambda.multiply(p1.x.subtract(xr)).subtract(p1.y).mod(P);
        return new Point(xr, yr);
    }
    // Point doubling on the elliptic curve
    private static Point pointDouble(Point p) {
        BigInteger lambda = p.x.pow(2).multiply(BigInteger.valueOf(3)).add(A)
                .multiply(p.y.multiply(BigInteger.valueOf(2)).modInverse(P)).mod(P);
        BigInteger xr = lambda.pow(2).subtract(p.x.multiply(BigInteger.valueOf(2))).mod(P);
        BigInteger yr = lambda.multiply(p.x.subtract(xr)).subtract(p.y).mod(P);
        return new Point(xr, yr);
    }
    // Scalar multiplication using double-and-add
    private static Point scalarMultiply(BigInteger k, Point p) {
        Point result = new Point(BigInteger.ZERO, BigInteger.ZERO); // Neutral element
        Point addend = p;
        while (k.compareTo(BigInteger.ZERO) > 0) {
            if (k.and(BigInteger.ONE).equals(BigInteger.ONE)) {
                result = pointAdd(result, addend);
            }
            addend = pointDouble(addend);
            k = k.shiftRight(1);
        }
        return result;
    }
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        // Generate private keys for Alice and Bob
        BigInteger dA = new BigInteger(256, random).mod(N); // Alice's private key
        BigInteger dB = new BigInteger(256, random).mod(N); // Bob's private key
        // Calculate public keys
        Point G = new Point(Gx, Gy);
        Point QA = scalarMultiply(dA, G); // Alice's public key
        Point QB = scalarMultiply(dB, G); // Bob's public key
        // Calculate shared secrets
        Point sharedSecretA = scalarMultiply(dA, QB);
        Point sharedSecretB = scalarMultiply(dB, QA);
        // Display results
        System.out.println("Alice's Private Key: " + dA.toString(16));
        System.out.println("Bob's Private Key: " + dB.toString(16));
        System.out.println("Alice's Public Key: (" + QA.x.toString(16) + ", " + QA.y.toString(16) +
                ")");
        System.out.println("Bob's Public Key: (" + QB.x.toString(16) + ", " + QB.y.toString(16) +
                ")");
        System.out.println("Shared Secret (Alice): (" + sharedSecretA.x.toString(16) + ", " +
                sharedSecretA.y.toString(16) + ")");
        System.out.println("Shared Secret (Bob): (" + sharedSecretB.x.toString(16) + ", " +
                sharedSecretB.y.toString(16) + ")");
    }
}

