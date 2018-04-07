package pt.server.ulisboa.tecnico.cmu.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.GeneralSecurityException;
import java.util.TreeMap;

import pt.server.ulisboa.tecnico.cmu.server.handlers.HelloCommandHandler;
import pt.shared.ServerAndClientGeneral.Exceptions.MethodNotFoundException;
import pt.shared.ServerAndClientGeneral.command.CommandHandler;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.command.Command;

public class Server {

    public static void main(String[] args) {
        CommandHandler cmdHandler;
        ServerSocket socket = null;
        String host = args[0];
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
            } catch (MethodNotFoundException e) {
                e.printStackTrace();
                continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static CommandHandler handlerFinder(Command command) throws GeneralSecurityException, IOException, MethodNotFoundException {
        TreeMap argsMap = command.getArguments();
        System.out.println("Received: " + command.getMessage());
        switch (command.getId()) {

            case "HelloCommand":
                return new HelloCommandHandler();
                /*
            case "SignUpCommand":
                String Username = (String) argsMap.get("Username");
                String busTicketCode = (String) argsMap.get("busTicketCode");
                try {
                    signUpHandle(Username, busTicketCode);
                    return new SignUpResponse();
                } catch (SecException e) {
                    e.printStackTrace();
                    return new ErrorResponse(e.getMessage());
                }
            case "LoginCommand":
                Username = (String) argsMap.get("Username");
                busTicketCode = (String) argsMap.get("busTicketCode");
                double sessionId;
                try {
                    sessionId = loginHandle(Username, busTicketCode);
                    return new LogInResponse(sessionId);
                } catch (InvalidLoginException e) {
                    e.printStackTrace();
                    return new ErrorResponse(e.getMessage());
                } catch (SessionIdException e) {
                    e.printStackTrace();
                    return new ErrorResponse(e.getMessage());
                }
            case "LogOutCommand":
                Username = (String) argsMap.get("Username");
                try {
                    logOutHandle(Username);
                    return new LogOutResponse();
                } catch (InvalidLoginException e) {
                    e.printStackTrace();
                    return new ErrorResponse(e.getMessage());
                }
            case "ListTourLocationsCommand":
                return new ListTourResponse();

            case "DownloadQuizQuestionsCommand":
                return new DownloadQuizQuestionsResponse();

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
