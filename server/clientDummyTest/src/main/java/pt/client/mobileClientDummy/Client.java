package pt.client.mobileClientDummy;

import pt.client.mobileClientDummy.handlers.HelloClientHandlerImpl;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.HelloCommand;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    Socket server = null;
    String reply = null;
    String[] _args;
    boolean isServerUp;
    int tries = 0;

    public Client(Socket server) {
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

    public void eraseDatabaseForTest() {
        //server.eraseDatabaseForTests();
    }

    // TODO this methods go into task handler
    public void ping(String msg) {
        Command cmd = new HelloCommand(msg);
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
}
