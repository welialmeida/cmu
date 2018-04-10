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
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
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

    public Client(Socket server, String pubKFilename) {
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
        Command cmd = new HelloCommand(msg, argsMap, this.privKey, this.pubK);
        Response rsp = null;
        try {
            rsp = start(cmd);
            tries = 0;
        } catch (FailedToConnectToServer failedToConnectToServer) {
            failedToConnectToServer.printStackTrace();
            if(tries != 3 || !isServerUp) {
                ping(msg);
                tries++;
            }
        }
        rsp.handle(new HelloClientHandlerImpl());
        return;
    }

    private void reconnect() {
        try {
            Thread.sleep(6000);
            isServerUp = true;
        } catch (InterruptedException e) {
            System.exit(1);
        }
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
