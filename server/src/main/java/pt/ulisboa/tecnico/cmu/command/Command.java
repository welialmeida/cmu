package pt.ulisboa.tecnico.cmu.command;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pt.ulisboa.tecnico.cmu.response.Response;

public abstract class Command implements Serializable {

    private TreeMap<Double, Double> nonceListsSend; // nonceList of used nonces for each unique session id
    private List<Object> argsList;
    private TreeMap<String, Object> argsMap;
    private byte[] signature;

    public abstract Response handle(CommandHandler ch);

    public abstract String getMessage();

    public abstract String getId();

    //TODO - nonce
    //TODO - signature
    public Command(PublicKey pubK, byte[] signature) {
        argsList = new ArrayList<>();
        argsMap = new TreeMap<>();
        genNonce(pubK);
    }

    public Command() {

    }

    //TODO - nonce
    private void genNonce(PublicKey pubK) {

    }

    public TreeMap getArguments() {
        return argsMap;
    }

    //TODO - signature
    private void genSignature() {

    }

    //TODO - signature
    public byte[] getSignature() {
        return null;
    }

    public Object getArgument(String argName) {
        return argsMap.get(argName);
    }

    private void addToArgsList(Object obj) {
        argsList.add(obj);
    }

    public void addToArgs(String argName, Object arg) {
        addToArgsList(arg);
        argsMap.put(argName, arg);
    }
}
