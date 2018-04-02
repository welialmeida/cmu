package pt.ulisboa.tecnico.cmu.server;

import pt.ulisboa.tecnico.cmu.ServerAndClientGeneral.RSAKeyHandling;
import pt.ulisboa.tecnico.cmu.ServerAndClientGeneral.SecException;
import pt.ulisboa.tecnico.cmu.command.Command;
import pt.ulisboa.tecnico.cmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.response.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.TreeMap;

public class CommandHandlerImpl implements CommandHandler {

    //TODO session Ids are SecureRandom Doubles - login
    //TODO nonces are SecureRandom Doubles - nonce
    private TreeMap<Double, Double> nonceListsReceived; // nonceList of used nonces for each unique session id
    private TreeMap<Double, PublicKey> idMap; // unique session ids for login
    private TreeMap<String, String> signUpMap; // unique name as key, unique code as value
    private PrivateKey privKey;
    private PublicKey pubKey;
    private String keyFilename = "server";

    public CommandHandlerImpl() throws GeneralSecurityException, IOException {
        nonceListsReceived = new TreeMap<>();
        idMap = new TreeMap<>();
        signUpMap = new TreeMap<>();
        privKey = RSAKeyHandling.getPrivKey(keyFilename);
        pubKey = RSAKeyHandling.getPuvKey(keyFilename);
        this.getPersistentItems();
    }

    private String generateSessionId() {
        return null;
    }

    //TODO - nonce
    private void verifyNonce(PublicKey pubK, double nonce) {
        //verify with nonceLists

    }

    //TODO - persistence
    private void getPersistentItems() {

    }

    //TODO - persistence
    private void addUsernameAndTicketCodeToPersistentStorage(String Username, String busTicketCode) {
        // format: user + "|" + busTicketCode

    }

    private void signUpHandle(String Username, String busTicketCode) throws SecException {
        if (Username == null || busTicketCode == null)
            throw new SecException("null username of busTicketCode");
        if(signUpMap.containsKey(Username)) {
            throw new SecException("username already exists");
        } else if (signUpMap.containsValue(busTicketCode)) {
            throw new SecException("ticket code already exists");
        } else {
            signUpMap.put(Username, busTicketCode);
            addUsernameAndTicketCodeToPersistentStorage(Username, busTicketCode);
        }
        return;
    }

    //TODO - login
    private double loginHandle(String Username, String busTicketCode) {
        // busTicketCode is password
        // returns sessionId
        return 0.0;
    }

    @Override
    public Response handle(Command command) {
        // main method
        TreeMap argsMap = command.getArguments();
        System.out.println("Received: " + command.getMessage());
        switch (command.getId()) {
            case "DownloadQuizQuestionsCommand":
                return new DownloadQuizQuestionsResponse();
            case "HelloCommand":
                return new HelloResponse();
            case "ListTourLocationsCommand":
                return new ListTourResponse();
            case "LoginCommand":
                String Username = (String) argsMap.get("Username");
                String busTicketCode = (String) argsMap.get("busTicketCode");
                double sessionId = loginHandle(Username, busTicketCode);
                return new LogInResponse(sessionId);
            case "PostQuizAnswersForMonumentCommand":
                return new PostQuizAnswersForOneMonumentResponse();
            case "ReadQuizResultsCommand":
                return new ReadQuizResultsResponse();
            case "SignUpCommand":
                Username = (String) argsMap.get("Username");
                busTicketCode = (String) argsMap.get("busTicketCode");
                try {
                    signUpHandle(Username, busTicketCode);
                } catch (SecException e) {
                    e.printStackTrace();
                    return new ErrorResponse(e.getMessage());
                }
                return new SignUpResponse();
            default:
                HelloResponse hr = new HelloResponse();
                hr.setMessage("error setting response");
                return hr;
        }
    }
}
