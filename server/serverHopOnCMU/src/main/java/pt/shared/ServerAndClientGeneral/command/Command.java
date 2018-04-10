package pt.shared.ServerAndClientGeneral.command;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.response.Response;

public abstract class Command implements Serializable {

    //TODO signature and nonce
    private TreeMap<String, Object> argsMap;
    private byte[] signature;
    private double nonce;
    private double sessionId;
    private PublicKey pubK;

    public abstract Response handle(CommandHandler ch);

    public abstract String getMessage();

    public abstract String getId();

    //TODO - nonce
    //TODO - signature
    public Command(double sessionId, TreeMap<String, Object> argsMap, PrivateKey clientPrivKey, PublicKey pubK) {
        //session id and security
        this.argsMap = argsMap;
        this.argsMap.put("id", this.getId());
        setSessionId(sessionId);
        this.nonce = genNonce();
        this.argsMap.put("nonce", this.nonce);
        this.pubK = pubK;
        this.argsMap.put("pubK", this.pubK);
        this.signature = genSignature(clientPrivKey);

    }

    public Command(TreeMap<String, Object> argsMap, PrivateKey clientPrivKey, PublicKey pubK) {
        //security
        this.argsMap = argsMap;
        this.argsMap.put("id", this.getId());
        this.nonce = genNonce();
        this.argsMap.put("nonce", this.nonce);
        this.pubK = pubK;
        this.argsMap.put("pubK", this.pubK);
        this.signature = genSignature(clientPrivKey);
    }

    public Command(TreeMap<String, Object> argsMap) {
        //basic
        this.argsMap = argsMap;
        this.argsMap.put("id", this.getId());
        this.nonce = genNonce();
    }

    public boolean equals(Object obj) {
        // checks if args are equal in both objects

        if (!this.getClass().equals(obj.getClass())) {
            System.out.println("different classes");
            return false;
        }

        Command cmd = (Command)obj;
        for (String key : cmd.getArguments().keySet()) {
            if (this.getArguments().containsKey(key)) {
                if(!this.getArguments().containsValue(cmd.getArgument(key))) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private void setSessionId(double sessionId) {
        this.sessionId = sessionId;
        addToArgs("sessionID", sessionId);
    }

    private double getSessionId() {
        return this.sessionId;
    }

    //TODO - nonce
    private double genNonce() {

        return 0.0;
    }

    //TODO - signature
    public byte[] genSignature(PrivateKey privKey) {
        //List args = argsMapToList();
        //Signature signature = SignatureHandling.createSignature(privKey, );
        //addToArgs("signature", signature) //add to argsMap
        //return signature;
        return null;
    }

    public TreeMap<String, Object> getArguments() {
        return argsMap;
    }

    //TODO - signature
    public byte[] getSignature() {
        return this.signature;
    }

    public Object getArgument(String argName) {
        return argsMap.get(argName);
    }

    public void addToArgs(String argName, Object arg) {
        argsMap.put(argName, arg);
    }
}
