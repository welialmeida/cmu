package pt.server.ulisboa.tecnico.cmu.server;

import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.AccountNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.InvalidLoginException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.PublicKeyNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.WrongSessionIdException;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.command.SignInCommand;
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

    private static List<Account> cacheAccountList = new ArrayList<>(); // cache
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

    public Account getPersistentItem(String username, String busTicketCode) throws AccountNotFoundException {
        // get Persistent Items into signUpMap
        Account acc = null;
        for(Account account : cacheAccountList) {
            if(account.getUsername().equals(username) && account.getBusTicketCode().equals(busTicketCode)) {
                return account;
            }
        }
        try {
            if(Persistence.exists(username, busTicketCode)) {
                acc = Persistence.read(username, busTicketCode);
                cacheAccountList.add(acc);
                return acc;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new AccountNotFoundException("account not found");
    }

    public void addUsernameAndTicketCodeToPersistentStorage(String username, String busTicketCode, PublicKey pubK) {
        Account acc = new Account(username, busTicketCode, pubK);
        this.cacheAccountList.add(acc);
        try {
            Persistence.store(acc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getcacheAccountList() {
        return this.cacheAccountList;
    }

    @Override
    public Response handle(Command command) {

        System.out.println(command.getArguments());
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

    public Account getAndCheckAccount(Command cmd, String username, String busTicketCode)
            throws IOException, ClassNotFoundException, AccountNotFoundException, PublicKeyNotFoundException {

        // check if account exists in server and checks if public key in server is the same as the used publicKey

        PublicKey pubK = cmd.getPubK();
        String msg;
        Account databaseAccount;

        //  check locally
        for (Account acc : cacheAccountList) {
            if(acc.getUsername().equals(username) && acc.getBusTicketCode().equals(busTicketCode)) {
                if(acc.getPubK().toString().equals(pubK.toString())) {
                    return acc;
                } else {
                    msg = "pubK not found in server";
                    throw new PublicKeyNotFoundException(msg);
                }
            }
        }

        if(!Persistence.exists(username, busTicketCode)) {
            msg = "account non existing";
            throw new AccountNotFoundException(msg);
        } else {
            databaseAccount = Persistence.read(username, busTicketCode);
            cacheAccountList.add(databaseAccount);
            if(!databaseAccount.getPubK().toString().equals(pubK.toString())) {
                msg = "pubK not found in server Account";
                throw new PublicKeyNotFoundException(msg);
            }
        }
        return databaseAccount;
    }

    public Account getAndCheckAccount(Command cmd, String username, String busTicketCode, Double sessionId)
            throws IOException, ClassNotFoundException, AccountNotFoundException, PublicKeyNotFoundException,
            WrongSessionIdException {

        // check if account exists in server and checks if public key in server is the same as the used publicKey

        Account databaseAcc = getAndCheckAccount(cmd, username, busTicketCode);
        if(!databaseAcc.getSessionId().equals(sessionId)) {
            throw new WrongSessionIdException("sessionId does not match currently used sessionID");
        }

        return databaseAcc;
    }

}
