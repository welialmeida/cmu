package pt.server.ulisboa.tecnico.cmu.server;

import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.AccountNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.InvalidLoginException;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.response.Error.ErrorResponse;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.util.RSAKeyHandling;
import pt.shared.ServerAndClientGeneral.Exceptions.SessionIdException;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.CommandHandler;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class CommandHandlerImpl implements CommandHandler {

    //TODO session Ids are SecureRandom Doubles - login
    //TODO nonces are SecureRandom Doubles - nonce
    // TODO client generates key when he sends signUpCommand

    private static TreeMap<PublicKey, Double> nonceListsReceived; // nonceList of used nonces for each unique session id
    private static TreeMap<Double, PublicKey> idMap; // unique session ids for login
    private static TreeMap<String, Double> signInMap; // unique name as key, sessionId as value
    private static List<Account> signUpList; // cache
    private PrivateKey privKey;
    private PublicKey pubKey;
    private String keyFilename = "server";
    private SecureRandom random;
    private String secureRandAlgorithm = "SHA1PRNG";

    public CommandHandlerImpl() throws GeneralSecurityException, IOException {
        nonceListsReceived = new TreeMap<>();
        signInMap = new TreeMap<>();
        idMap = new TreeMap<>();
        signUpList = new ArrayList<>();
        privKey = RSAKeyHandling.getPrivKey(keyFilename);
        pubKey = RSAKeyHandling.getPubKey(keyFilename);
        random = SecureRandom.getInstance(secureRandAlgorithm);
    }

    private String generateSessionId() {
        return null;
    }

    //TODO - nonce
    private void verifyNonce(PublicKey pubK, double nonce) throws NonceErrorException{
        //verify with nonceLists

    }

    //TODO - signature
    private void verifySignature(PublicKey pubK) throws SecException{

    }

    public Account getPersistentItem(String busTicketCode) throws AccountNotFoundException {
        // get Persistent Items into signUpMap
        Account acc = null;
        for(Account account : signUpList) {
            if(account.getBusTicketCode().equals(busTicketCode)) {
                return account;
            }
        }
        try {
            acc = Persistence.read(busTicketCode);
            signUpList.add(acc);
            return acc;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AccountNotFoundException("account not found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new AccountNotFoundException("class account not found");
        }
    }

    public void addUsernameAndTicketCodeToPersistentStorage(String username, String busTicketCode, PublicKey pubK) {
        Account acc = new Account(username, busTicketCode, pubK);
        this.signUpList.add(acc);
        try {
            Persistence.store(busTicketCode, acc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double genSessionId() throws SessionIdException {
        double newSeed = random.nextDouble();
        if (idMap.containsKey(newSeed)) {
            throw new SessionIdException("sessionId had already been assigned on session Id creation");
        }
        return newSeed;
    }

    public List<Account> getSignUpList() {
        return this.signUpList;
    }

    //TODO - login
    private double loginHandle(Account acc) throws InvalidLoginException, SessionIdException {
        // busTicketCode is password
        double sessionId;
        String username;
        String busTicketCode;
        if (signUpList.contains(acc)) {
            username = acc.getUsername();
            busTicketCode = acc.getBusTicketCode();
            sessionId = genSessionId();
            if (signInMap.containsKey(username)) {
                throw new InvalidLoginException("Username already logged in");
            } else {
                signInMap.put(username, sessionId);
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

        double nonce = (double) command.getArgument("nonce");
        PublicKey pubK = (PublicKey) command.getArgument("pubK");
        TreeMap<String, Object> argsMap = new TreeMap<>();

        try {
            verifyNonce(pubK, nonce);
        } catch (NonceErrorException e) {
            e.printStackTrace();
            return new ErrorResponse(argsMap, "nonce check failed");
        }

        try {
            verifySignature(pubK);
        } catch (SecException e) {
            e.printStackTrace();
            return new ErrorResponse(argsMap, "signature check failure");
        }

        return null;
    }

    public static void addToSessionIds(String busTicketCode, double sessionId) {
        signInMap.put(busTicketCode, sessionId);
    }

    public static double getSessionId(String busTicketCode) {
        return signInMap.get(busTicketCode);
    }
}
