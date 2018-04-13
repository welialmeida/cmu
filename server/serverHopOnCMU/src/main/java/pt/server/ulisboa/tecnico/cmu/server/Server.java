package pt.server.ulisboa.tecnico.cmu.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;

import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.InvalidSignupException;
import pt.server.ulisboa.tecnico.cmu.server.handlers.*;
import pt.shared.ServerAndClientGeneral.Exceptions.MethodNotFoundException;
import pt.shared.ServerAndClientGeneral.command.CommandHandler;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.command.Command;

public class Server {

    public static void main(String[] args) {
        CommandHandler cmdHandler;
        ServerSocket socket = null;
        //String host = args[0];
        int port = Integer.parseInt(args[1]);


        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket client = null;

        ServerSocket finalSocket = socket;
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Server now closed.");
                try {
                    finalSocket.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        System.out.println("server ready! ");

        while (true) {
            System.out.println();
            System.out.println("Server is accepting connections at "+ port);
            System.out.println("ctrl+c to shutdown");
            System.out.println();

            try {
                client = socket.accept();

                ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                Command cmd = (Command) ois.readObject();
                cmdHandler = handlerFinder(cmd);
                Response rsp = cmd.handle(cmdHandler);

                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(rsp);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (InvalidSignupException e) {
                e.printStackTrace();
            } catch (MethodNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static CommandHandler handlerFinder(Command command) throws GeneralSecurityException, IOException,
            MethodNotFoundException, InvalidSignupException {

        switch (command.getId()) {
            case "HelloCommand":
                return new HelloCommandHandler();
            case "SignUpCommand":
                return new SignUpCommandHandler();
            case "SignInCommand":
                return new SignInCommandHandler();
            case "SignOutCommand":
                return new SignOutCommandHandler();
            case "ListTourLocationsCommand":
                return new ListTourCommandHandler();
            case "DownloadQuizQuestionsCommand":
                return new DownloadQuizQuestionsResultsHandler();
            /*
            case "PostQuizAnswersForMonumentCommand":
                return new PostQuizAnswersForOneMonumentResponse();

            case "ReadQuizResultsCommand":
                return new ReadQuizResultsResponse();
                */
            default:
                throw new MethodNotFoundException("no method found");
        }
    }
}
