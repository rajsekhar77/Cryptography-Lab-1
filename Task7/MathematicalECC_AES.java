package Task7;

import java.math.BigInteger;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
public class MathematicalECC_AES {
    public static void main(String[] args) {
        String message = "Hell0 SRM AP";
        System.out.println("Curve 1: secp256k1");
        ECCCurve curve1 = new ECCCurve(
                new BigInteger("0"), // a
                new BigInteger("7"), // b
                new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16) // p
                );
        performECC_AES(curve1, message);
        System.out.println("\nCurve 2: secp192r1");
        ECCCurve curve2 = new ECCCurve(
                new BigInteger("-3"), // a
                new BigInteger("2455155546008943817740293915197451784769108058161191238065"),
// b
                new BigInteger("6277101735386680763835789423207666416083908700390324961279")
// p
        );
        performECC_AES(curve2, message);
    }
    private static void performECC_AES(ECCCurve curve, String message) {
        try {
            // Generate a random private key
            BigInteger privateKey = new BigInteger(curve.p.bitLength(), new SecureRandom());
            ECCPoint publicKey = curve.multiplyPoint(curve.basePoint(), privateKey);
            // Create a shared secret from the public key and private key
            ECCPoint sharedSecret = curve.multiplyPoint(publicKey, privateKey);
            byte[] sharedKey = sharedSecret.x.toByteArray();
            // AES Encryption
            SecretKeySpec secretKey = new SecretKeySpec(sharedKey, 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessage = cipher.doFinal(message.getBytes());
            System.out.println("Encrypted: " + Base64.getEncoder().encodeToString(encryptedMessage));
            // AES Decryption
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedMessage = cipher.doFinal(encryptedMessage);
            System.out.println("Decrypted: " + new String(decryptedMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class ECCCurve {
    public BigInteger a, b, p;
    public ECCCurve(BigInteger a, BigInteger b, BigInteger p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }
    public ECCPoint basePoint() {
        // Returning a fixed base point for simplicity
        return new ECCPoint(BigInteger.valueOf(4), BigInteger.valueOf(20));
    }
    public ECCPoint addPoints(ECCPoint P, ECCPoint Q) {
        if (P.isInfinity()) return Q;
        if (Q.isInfinity()) return P;
        BigInteger lambda;
        if (P.x.equals(Q.x)) {
            if (!P.y.equals(Q.y) || P.y.equals(BigInteger.ZERO)) return ECCPoint.infinity();
            lambda = (P.x.pow(2).multiply(BigInteger.valueOf(3)).add(a))
                    .multiply(P.y.multiply(BigInteger.valueOf(2)).modInverse(p)).mod(p);
        } else {
            lambda = (Q.y.subtract(P.y)).multiply(Q.x.subtract(P.x).modInverse(p)).mod(p);
        }
        BigInteger x3 = lambda.pow(2).subtract(P.x).subtract(Q.x).mod(p);
        BigInteger y3 = lambda.multiply(P.x.subtract(x3)).subtract(P.y).mod(p);
        return new ECCPoint(x3, y3);
    }
    public ECCPoint multiplyPoint(ECCPoint P, BigInteger n) {
        ECCPoint R = ECCPoint.infinity();
        ECCPoint Q = P;
        while (n.compareTo(BigInteger.ZERO) > 0) {
            if (n.and(BigInteger.ONE).equals(BigInteger.ONE)) {
                R = addPoints(R, Q);
            }
            Q = addPoints(Q, Q);
            n = n.shiftRight(1);
        }
        return R;
    }
}
class ECCPoint {
    public BigInteger x, y;

    public ECCPoint(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public static ECCPoint infinity() {
        return new ECCPoint(null, null);
    }

    public boolean isInfinity() {
        return x == null || y == null;
    }
}