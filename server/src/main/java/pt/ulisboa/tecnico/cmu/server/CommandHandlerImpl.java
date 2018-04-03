package pt.ulisboa.tecnico.cmu.server;

import pt.ulisboa.tecnico.cmu.ServerAndClientGeneral.RSAKeyHandling;
import pt.ulisboa.tecnico.cmu.ServerAndClientGeneral.SecException;
import pt.ulisboa.tecnico.cmu.ServerAndClientGeneral.SessionIdException;
import pt.ulisboa.tecnico.cmu.command.Command;
import pt.ulisboa.tecnico.cmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmu.response.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class CommandHandlerImpl implements CommandHandler {

    //TODO session Ids are SecureRandom Doubles - login
    //TODO nonces are SecureRandom Doubles - nonce
    private TreeMap<Double, Double> nonceListsReceived; // nonceList of used nonces for each unique session id
    private TreeMap<Double, PublicKey> idMap; // unique session ids for login
    private TreeMap<String, Double> signInMap; // unique name as key, uniqueId as value
    private TreeMap<String, String> signUpMap; // unique name as key, unique code as value
    private PrivateKey privKey;
    private PublicKey pubKey;
    private String keyFilename = "server";
    private SecureRandom random;
    private String secureRandAlgorithm = "SHA1PRNG";

    public CommandHandlerImpl() throws GeneralSecurityException, IOException {
        nonceListsReceived = new TreeMap<>();
        idMap = new TreeMap<>();
        signUpMap = new TreeMap<>();
        privKey = RSAKeyHandling.getPrivKey(keyFilename);
        pubKey = RSAKeyHandling.getPuvKey(keyFilename);
        this.getPersistentItems();
        random = SecureRandom.getInstance(secureRandAlgorithm);
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
        // get Persistent Items into signUpMap

    }

    //TODO - persistence
    private void addUsernameAndTicketCodeToPersistentStorage(String Username, String busTicketCode) {
        // format: user + "|" + busTicketCode

    }

    private void signUpHandle(String Username, String busTicketCode) throws SecException {
        if (Username == null || busTicketCode == null)
            throw new SecException("null username of busTicketCode");
        if (signUpMap.containsKey(Username)) {
            throw new SecException("username already exists");
        } else if (signUpMap.containsValue(busTicketCode)) {
            throw new SecException("ticket code already exists");
        } else {
            signUpMap.put(Username, busTicketCode);
            addUsernameAndTicketCodeToPersistentStorage(Username, busTicketCode);
        }
    }

    private double genSessionId() throws SessionIdException {
        double newSeed = random.nextDouble();
        if (idMap.containsKey(newSeed)) {
            throw new SessionIdException("sessionId had already been assigned on session Id creation");
        }
        return newSeed;
    }

    //TODO - login
    private double loginHandle(String Username, String busTicketCode) throws InvalidLoginException, SessionIdException {
        // busTicketCode is password
        double sessionId;
        if (signUpMap.containsKey(Username) && signUpMap.containsValue(busTicketCode)) {
            sessionId = genSessionId();
            if (signInMap.containsKey(Username)) {
                throw new InvalidLoginException("Username already logged in");
            } else {
                signInMap.put(Username, sessionId);
            }
            return sessionId;
        } else {
            throw new InvalidLoginException("invalid Login, no username of ticket code found");
        }
    }

    private void logOutHandle(String Username) throws InvalidLoginException {
        if (signInMap.containsKey(Username)) {
            signInMap.remove(Username);
        } else {
            throw new InvalidLoginException("Impossible to LogOut of session");
        }
    }

    @Override
    public Response handle(Command command) {
        // main method
        //TODO verifyNonce - nonce
        //TODO verifySignature - signature
        TreeMap argsMap = command.getArguments();
        System.out.println("Received: " + command.getMessage());
        switch (command.getId()) {

            case "HelloCommand":
                return new HelloResponse();

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

            default:
                return new ErrorResponse("nothing happened in server");
        }
    }
}
