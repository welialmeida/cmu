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

    public Client(Socket server) {
        this.server = server;
    }

    public Response start(Command cmd) {

        Response response = null;
        int tries = 0;
        isServerUp = true;

        while (tries++ < 3 || isServerUp) {
            try {
                if (server != null) {
                    //return server.ping();
                    ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
                    oos.writeObject(cmd);
                    ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
                    response = (Response) ois.readObject();

                    oos.close();
                    ois.close();

                    tries = 0;
                } else {
                    isServerUp = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                reconnect();
            }
        }

        return response;
    }

    public void eraseDatabaseForTest() {
        //server.eraseDatabaseForTests();
    }

    public void ping(String msg) {
        Command cmd = new HelloCommand(msg);
        Response rsp = start(cmd);
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
