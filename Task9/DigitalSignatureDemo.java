package Task9;
import java.security.*;
import java.util.*;

public class DigitalSignatureDemo {
    static class Client {
        String id;
        KeyPair keyPair;

        public Client(String id) throws Exception {
            this.id = id;
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC"); // or "RSA"
            keyGen.initialize(256);
            this.keyPair = keyGen.generateKeyPair();
        }

        public byte[] signMessage(byte[] message) throws Exception {
            Signature ecdsa = Signature.getInstance("SHA256withECDSA");
            ecdsa.initSign(this.keyPair.getPrivate());
            ecdsa.update(message);
            return ecdsa.sign();
        }

        public PublicKey getPublicKey() {
            return this.keyPair.getPublic();
        }
    }

    static class Server {
        Map<String, PublicKey> clientPublicKeys = new HashMap<>();

        public void registerClient(Client client) {
            clientPublicKeys.put(client.id, client.getPublicKey());
        }

        public boolean verifySignature(String clientId, byte[] message, byte[] signature) throws Exception {
            PublicKey publicKey = clientPublicKeys.get(clientId);
            if (publicKey == null) return false;

            Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(message);
            return ecdsaVerify.verify(signature);
        }
    }

    public static void main(String[] args) throws Exception {
        int numClients = 5;
        List<Client> clients = new ArrayList<>();
        Server server = new Server();

        // Register clients
        for (int i = 1; i <= numClients; i++) {
            Client client = new Client("Client_" + i);
            server.registerClient(client);
            clients.add(client);
        }

        // Simulate signing and verification
        for (Client client : clients) {
            String msg = "Hello from " + client.id + "!";
            byte[] message = msg.getBytes();
            byte[] signature = client.signMessage(message);

            boolean verified = server.verifySignature(client.id, message, signature);
            System.out.println("[" + client.id + "] Message: " + msg);
            System.out.println("[" + client.id + "] Signature Verified: " + verified);
            System.out.println("--------------------------------------------------");
        }
    }
}
