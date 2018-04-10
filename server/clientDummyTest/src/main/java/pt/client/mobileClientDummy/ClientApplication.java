package pt.client.mobileClientDummy;

import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.util.RSAKeyHandling;
import pt.shared.ServerAndClientGeneral.util.SignatureHandling;

import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.util.*;

public class ClientApplication {
    private static PublicKey serverPubKey;
    private static List<Double> receivingNonceList = new ArrayList();
    private static SecureRandom random;
    static Socket server;
    static Client client;

    public static void main(String[] args) {

        String pubKFilenameInUse = "client1";
        String serverIp = args[0];
        int serverPort = Integer.parseInt(args[1]);
        try {
            server = new Socket(serverIp, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client = new Client(server, pubKFilenameInUse);
        
        try {
            serverPubKey = RSAKeyHandling.getPubKey("server");
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

    public static void ping(String message) {
        if (client == null) {
            System.out.println("Server is not available");
            return;
        }
        client.ping(message);
    }

    public static void signUp() {
        return;
    }

    public static void signIn() {
        return;
    }

    public static void listTourLocations() {
        return;
    }

    public static void downloadQuizLocations() {
        return;
    }

    public static void postAnswers() {
        return;
    }

    public static void readResults() {
        return;
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

}
