package pt.client.mobileClientDummy;

import pt.client.mobileClientDummy.exceptions.FailedToConnectToServer;
import pt.client.mobileClientDummy.handlers.HelloClientHandlerImpl;
import pt.client.mobileClientDummy.handlers.SignInClientHandler;
import pt.client.mobileClientDummy.handlers.SignUpClientHandler;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.HelloCommand;
import pt.shared.ServerAndClientGeneral.command.SignInCommand;
import pt.shared.ServerAndClientGeneral.command.SignUpCommand;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.util.RSAKeyHandling;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.*;
import java.util.TreeMap;

public class Client {
    String[] _args;
    boolean isServerUp;
    int tries = 0;
    PublicKey serverPubK;
    PublicKey pubK;
    PrivateKey privKey;
    String serverPubKFilename = "server";
    private static String secureRandAlgorithm = "SHA1PRNG";
    private static SecureRandom random;
    private Socket server;
    private String serverIp;
    private int serverPort;

    public Client(String serverIp, int serverPort) {
        if(random == null) {
            try {
                random = SecureRandom.getInstance(secureRandAlgorithm);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        try {
            this.serverPubK = RSAKeyHandling.getPubKey(serverPubKFilename);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.serverIp = serverIp;
        this.serverPort = serverPort;

    }

    public Response start(Command cmd) throws FailedToConnectToServer {

        Response response = null;
        isServerUp = true;

        try {
            //return server.ping();
            try {
                this.server = new Socket(serverIp, serverPort);
            } catch (IOException e) {
                e.printStackTrace();
                throw new FailedToConnectToServer("couldnt connect to server");
            }
            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(cmd);
            System.out.println("command: \n");
            System.out.println(cmd.getArguments());
            System.out.println("end of command\n");
            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            response = (Response) ois.readObject();
            System.out.println("response: \n");
            System.out.println(response.getArguments());
            System.out.println("end of response\n");
            oos.close();
            ois.close();
            this.server.close();
            return response;
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
    public void ping(String msg, String pubKFilename) {

        setKeysWithFileName(pubKFilename);

        TreeMap<String, Object> argsMap = new TreeMap<>();
        argsMap.put("return", msg);
        Command cmd = new HelloCommand(argsMap, this.privKey, this.pubK, this.random);
        Response rsp = null;
        try {
            rsp = start(cmd);
            tries = 0;
        } catch (FailedToConnectToServer e) {
            e.printStackTrace();
        }
        rsp.handle(new HelloClientHandlerImpl());
        return;
    }

    public void signUp(String username, String busTicketCode, String pubKFilename) {

        setKeysWithFileName(pubKFilename);

        TreeMap<String, Object> argsMap = new TreeMap<>();
        TreeMap<String, String> ret = new TreeMap<>();
        ret.put("username", username);
        ret.put("busTicketCode", busTicketCode);
        argsMap.put("return", ret);
        Command cmd = new SignUpCommand(argsMap, this.privKey, this.pubK, this.random);
        Response rsp = null;
        try {
            rsp = start(cmd);
            tries = 0;
        } catch (FailedToConnectToServer e) {
            e.printStackTrace();
        }
        rsp.handle(new SignUpClientHandler());
        return;
    }

    public void signIn(String username, String busTicketCode, String pubKFilename) {
        setKeysWithFileName(pubKFilename);

        TreeMap<String, Object> argsMap = new TreeMap<>();
        TreeMap<String, String> ret = new TreeMap<>();
        ret.put("username", username);
        ret.put("busTicketCode", busTicketCode);
        argsMap.put("return", ret);

        Command cmd = new SignInCommand(argsMap, this.privKey, this.pubK, this.random);
        Response rsp = null;
        try {
            rsp = start(cmd);
            tries = 0;
        } catch (FailedToConnectToServer e) {
            e.printStackTrace();
        }
        rsp.handle(new SignInClientHandler());
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
