package pt.client.mobileClientDummy;

import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.util.RSAKeyHandling;
import pt.shared.ServerAndClientGeneral.util.SignatureHandling;

import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.util.*;

public class ClientApplication {
    private PublicKey pubKey = null;
    private PrivateKey privKey = null;
    private double nonce = 0;
    private static PublicKey serverPubKey;
    private List<Double> receivingNonceList = new ArrayList();
    private List<Double> sendingNonceList = new ArrayList();
    private static SecureRandom random;
    static Socket server;
    static Client client;

    public static void main(String[] args) {

        String serverIp = args[0];
        int serverPort = Integer.parseInt(args[1]);
        try {
            server = new Socket(serverIp, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client = new Client(server);
        
        try {
            serverPubKey = RSAKeyHandling.getPuvKey("server");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        new ClientApplication().menu();
    }

    @SuppressWarnings("resource")
    public void menu() {
        Scanner keyboard;
        String line;

        while (true) {

            System.out.println("");
            System.out.println("1-generate key");
            System.out.println("2-signUp");
            System.out.println("3-signIn");
            System.out.println("4-List tour locations");
            System.out.println("5-Download quiz locations(at the corresponding monument)");
            System.out.println("6-Post quiz answers for one monument");
            System.out.println("7-Read quiz results (number of correct answers and ranking");
            System.out.println("8-ping server");
            System.out.println("0-SignOut\n");

            try {
                keyboard = new Scanner(System.in);
                int myint = keyboard.nextInt();
                String[] data;
                switch (myint) {
                    case 1:
                        System.out.println("Generate key");
                        System.out.println("Usage:<key-filename>");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        RSAKeyGenerator(line);
                        break;
                    case 2:
                        System.out.println("signUp");
                        System.out.println("Usage:<source key-filename>space<balance(integer)>");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        data = line.split(" ");
                        //signUp(data[0], Integer.parseInt(data[1]));
                        break;
                    case 3:
                        System.out.println("signIn");
                        System.out.println("Usage:<source key-filename>space<destination key-filename>space<balance(integer)>");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        data = line.split(" ");
                        //signIn(data[0], data[1], Integer.parseInt(data[2]));
                        break;
                    case 4:
                        System.out.println("list tour locations");
                        System.out.println("Usage:<source key-filename>");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        //listTourLocations(line);
                        break;
                    case 5:
                        System.out.println("download quiz locations");
                        System.out.println("Usage:<source key-filename>");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        //downloadQuizLocations(line);
                        break;
                    case 6:
                        System.out.println("post answers");
                        System.out.println("Usage:<target_to_audit key-filename>");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        //postAnswers(line);
                        break;
                    case 7:
                        System.out.println("read quiz results");
                        System.out.println("Usage:<source key-filename>");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        //readResults(line);
                        break;
                    case 8:
                        System.out.println("ping server");
                        System.out.println("write a message to be echoed back");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        //readResults(line);
                        ping(line);
                        break;
                    case 0:
                        System.out.println("Are you sure");
                        System.out.println("y/n");
                        keyboard = new Scanner(System.in);
                        line = keyboard.nextLine();
                        //logOut(line);
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please write number from 1 to 6 or 0");
                }
            } catch (InputMismatchException | NumberFormatException e) {
                e.printStackTrace();
                System.out.println("*** Please write number from 1 to 6 or 0\nException: ");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Please  verify the format of the inputs!!!\n");
            }
        }
    }

    public void ping(String message) {
        if (client == null) {
            System.out.println("Server is not available");
            return;
        }
        client.ping(message);
    }

    public void signUp() {
        return;
    }

    public void signIn() {
        return;
    }

    public void listTourLocations() {
        return;
    }

    public void downloadQuizLocations() {
        return;
    }

    public void postAnswers() {
        return;
    }

    public void readResults() {
        return;
    }

    private void genNonce() throws SecException {
        double newSeed = random.nextDouble();

        if (sendingNonceList.contains(newSeed)) {
            genNonce();
        } else {
            this.sendingNonceList.add(newSeed);
            this.nonce = newSeed;
        }
    }

    private byte[] getSignature(List argArray) {
        byte[] signature = null;
        try {
            signature = SignatureHandling.createSignature(privKey, argArray);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            System.out.println("Unable to create signature");
        }
        return signature;
    }

    private void verifyNonce(double nonce) throws SecException {
        if (receivingNonceList.contains(nonce)) {
            throw new SecException("nonce already used");
        } else {
            receivingNonceList.add(nonce);
        }
    }

    public static void RSAKeyGenerator(String path) {

        try {

            System.out.println("Generate and save keys");
            RSAKeyHandling.write(path);

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }


        System.out.println("Done.");

    }

    private void setKeysWithFileName(String fileName) {

        this.pubKey = null;
        this.privKey = null;
        try {
            this.pubKey = RSAKeyHandling.getPuvKey(fileName);
            this.privKey = RSAKeyHandling.getPrivKey(fileName);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            System.out.println("Unable to get public/private key");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to find public/private key file");
            return;
        }
    }

}
