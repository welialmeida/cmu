package pt.client.mobileClientDummy;

import pt.client.mobileClientDummy.exceptions.FailedToConnectToServer;
import pt.client.mobileClientDummy.handlers.HelloClientHandlerImpl;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.HelloCommand;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.util.RSAKeyHandling;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.TreeMap;

public class Client {
    Socket server = null;
    String[] _args;
    boolean isServerUp;
    int tries = 0;
    PublicKey serverPubK;
    PublicKey pubK;
    PrivateKey privKey;
    String serverPubKFilename = "server";
    private static String secureRandAlgorithm = "SHA1PRNG";
    private static SecureRandom random;

    public Client(Socket server, String pubKFilename) {
        if(random == null) {
            try {
                random = SecureRandom.getInstance(secureRandAlgorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        try {
            setKeysWithFileName(pubKFilename);
            this.serverPubK = RSAKeyHandling.getPubKey(serverPubKFilename);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.server = server;
    }

    public Response start(Command cmd) throws FailedToConnectToServer {

        Response response = null;
        isServerUp = true;

        try {
            if (server != null) {
                //return server.ping();
                    ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
                    oos.writeObject(cmd);
                    ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
                    response = (Response) ois.readObject();
                    oos.close();
                    ois.close();
                    return response;
            } else {
                isServerUp = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new FailedToConnectToServer("couldnt connect to server");
    }

    //TODO - IT tests skeleton
    public static void eraseDatabaseForTest() {
        //server.eraseDatabaseForTests();
    }

    // TODO this methods go into task handler
    public void ping(String msg) {
        TreeMap<String, Object> argsMap = new TreeMap<>();
        argsMap.put("return", msg);
        Command cmd = new HelloCommand(argsMap, this.privKey, this.pubK, this.random);
        Response rsp = null;
        try {
            rsp = start(cmd);
            tries = 0;
        } catch (FailedToConnectToServer e) {
            e.printStackTrace();
            if(tries < 3 || !isServerUp) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                ping(msg);
                tries++;
            }
        }
        rsp.handle(new HelloClientHandlerImpl());
        return;
    }

    private void setKeysWithFileName(String fileName) {

        this.pubK = null;
        this.privKey = null;
        try {
            this.pubK = RSAKeyHandling.getPubKey(fileName);
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
