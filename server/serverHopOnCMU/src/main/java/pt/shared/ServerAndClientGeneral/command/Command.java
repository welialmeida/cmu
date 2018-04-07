package pt.shared.ServerAndClientGeneral.command;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pt.shared.ServerAndClientGeneral.response.Response;

public abstract class Command implements Serializable {

    private TreeMap<Double, Double> nonceListsSend; // nonceList of used nonces for each unique session id
    private byte[] signature;
    private List<Object> argsList;
    private TreeMap<String, Object> argsMap;
    private double sessionId;

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

    public Command(PublicKey pubK, byte[] signature, double sessionId) {
        argsList = new ArrayList<>();
        argsMap = new TreeMap<>();
        genNonce(pubK);
        setSessionId(sessionId);
    }

    public Command() {

    }

    private void setSessionId(double sessionId) {
        this.sessionId = sessionId;
        addToArgs("sessionID", sessionId);
    }

    private double getSessionId() {
        return this.sessionId;
    }

    //TODO - nonce
    private void genNonce(PublicKey pubK) {

    }

    public TreeMap getArguments() {
        return argsMap;
    }

    //TODO - signature
    public byte[] getSignature() {
        return this.signature;
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
