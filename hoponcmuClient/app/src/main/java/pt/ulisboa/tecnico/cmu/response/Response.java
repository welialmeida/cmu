package pt.ulisboa.tecnico.cmu.response;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class Response implements Serializable {

    private TreeMap<Double, Double> nonceListsSend; // nonceList of used nonces for each unique session id
    private List<Object> argsList;
    private TreeMap<String, Object> argsMap;
    private byte[] signature;
    private double sessionId;

    //TODO - nonce
    //TODO - signature
    public Response(PublicKey pubK, byte[] signature) {
        argsList = new ArrayList<>();
        argsMap = new TreeMap<>();
        genNonce(pubK);
    }

    public Response(PublicKey pubK, byte[] signature, double sessionId) {
        argsList = new ArrayList<>();
        argsMap = new TreeMap<>();
        genNonce(pubK);
        setSessionId(sessionId);
    }

    public Response() {

    }

    private void setSessionId(double sessionId) {
        this.sessionId = sessionId;
        addToArgs("sessionID", sessionId);
    }

    private double getSessionId() {
        return this.sessionId;
    }

    public List getArgsList() {
        return argsList;
    }

    public TreeMap getArguments() {
        return argsMap;
    }

    //TODO - nonce
    private void genNonce(PublicKey pubK) {

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
