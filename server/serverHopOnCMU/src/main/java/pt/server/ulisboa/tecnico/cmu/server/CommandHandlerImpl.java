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
import pt.shared.ServerAndClientGeneral.util.SignatureHandling;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public abstract class CommandHandlerImpl implements CommandHandler {

    private static TreeMap<PublicKey, List<Double>> nonceListsReceived = new TreeMap<>(new Comparator<PublicKey>() {
        public int compare(PublicKey pub1, PublicKey pub2) {
            return pub1.toString().compareTo(pub2.toString());
        }
    });
    private static TreeMap<String, Double> signInMap = new TreeMap<>(); // unique name as key, sessionId as value
    private static List<Account> signUpList = new ArrayList<>(); // cache
    private static PrivateKey privKey;
    private static PublicKey pubKey;
    private static String keyFilename = "server";
    private static String secureRandAlgorithm = "SHA1PRNG";
    private static SecureRandom random;

    public CommandHandlerImpl() throws GeneralSecurityException, IOException {
        if(random == null) {
            random = SecureRandom.getInstance(secureRandAlgorithm);
        }
        if(privKey == null) {
            privKey = RSAKeyHandling.getPrivKey(keyFilename);
        }
        if(pubKey == null) {
            pubKey = RSAKeyHandling.getPubKey(keyFilename);
        }
    }

    //TODO - login
    private String generateSessionId() {
        return null;
    }

    private void verifyNonce(Command cmd) throws NonceErrorException {
        //verify with nonceLists
        PublicKey pubK = cmd.getPubK();
        double nonce = cmd.getNonce();

        if(nonceListsReceived.containsKey(pubK)) {
            List<Double> receivedNonceList = nonceListsReceived.get(pubK);
            if(receivedNonceList.contains(nonce)) {
                throw new NonceErrorException("failed nonce check");
            } else {
                receivedNonceList.add(nonce);
                nonceListsReceived.replace(pubK, receivedNonceList);
            }
        } else {
            List<Double> recvNonceList = new ArrayList<>();
            recvNonceList.add(nonce);
            nonceListsReceived.put(pubK, recvNonceList);
        }
    }

    private void verifySignature(Command cmd) throws SecException {
        SignatureHandling.checkSignature(cmd.getSignature(), cmd.getPubK(), cmd.argsToList());
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

    //TODO - login
    private double genSessionId() throws SessionIdException {
        //double newSeed = random.nextDouble();
        return 0.0;
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

        TreeMap<String, Object> argsMap = new TreeMap<>();

        try {
            verifyNonce(command);
        } catch (NonceErrorException e) {
            e.printStackTrace();
            String msg = "nonce check failed";
            argsMap.put("return", msg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

        try {
            verifySignature(command);
        } catch (SecException e) {
            e.printStackTrace();
            String msg = "signature check failure";
            argsMap.put("return", msg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

        return null;
    }

    public PrivateKey getPrivKey() {
        return privKey;
    }

    public PublicKey getPubKey() {
        return pubKey;
    }

    public static SecureRandom getRandom() {
        return random;
    }

    public static void addToSessionIds(String busTicketCode, double sessionId) {
        signInMap.put(busTicketCode, sessionId);
    }

    public static double getSessionId(String busTicketCode) {
        return signInMap.get(busTicketCode);
    }
}
