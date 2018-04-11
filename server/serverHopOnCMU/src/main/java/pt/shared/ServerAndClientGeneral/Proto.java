package pt.shared.ServerAndClientGeneral;

import pt.shared.ServerAndClientGeneral.util.SignatureHandling;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public abstract class Proto implements Serializable {

    private TreeMap<String, Object> argsMap;
    private byte[] signature;
    private double nonce;
    private double sessionId;
    private PublicKey pubK;

    public abstract String getId();

    public Proto(TreeMap<String, Object> argsMap, double sessionId, PrivateKey clientPrivKey,
                 PublicKey pubK, SecureRandom random) {

        this.argsMap = argsMap;
        this.argsMap.put("id", this.getId());
        setSessionId(sessionId);
        this.nonce = genNonce(random);
        this.argsMap.put("nonce", this.nonce);
        this.pubK = pubK;
        this.argsMap.put("pubK", this.pubK);
        this.signature = genSignature(clientPrivKey);
    }

    public Proto(TreeMap<String, Object> argsMap, PrivateKey clientPrivKey, PublicKey pubK, SecureRandom random) {

        this.argsMap = argsMap;
        this.argsMap.put("id", this.getId());
        this.nonce = genNonce(random);
        this.argsMap.put("nonce", this.nonce);
        this.pubK = pubK;
        this.argsMap.put("pubK", this.pubK);
        this.signature = genSignature(clientPrivKey);
    }


    public boolean equals(Object obj) {
        // checks if args are equal in both objects

        if (!this.getClass().equals(obj.getClass())) {
            System.out.println("different classes");
            return false;
        }

        Proto cmd = (Proto)obj;
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
        return (double) getArgument("sessionId");
    }

    private double genNonce(SecureRandom random) {
        double rand = random.nextDouble();
        return rand;
    }

    public byte[] genSignature(PrivateKey privKey) {
        byte[] sign = null;

        try {
            sign = SignatureHandling.createSignature(privKey, argsToList());
            addToArgs("signature", sign);
            return sign;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return sign;
    }

    public TreeMap<String, Object> getArguments() {
        return argsMap;
    }

    public PublicKey getPubK() {
        return (PublicKey) getArgument("pubK");
    }

    public double getNonce() {
        return (double) getArgument("nonce");
    }

    public byte[] getSignature() {
        return (byte[]) getArgument("signature");
    }

    public Object getReturn() {
        return getArgument("return");
    }

    public String toString() {
        return (String) getArgument("return");
    }

    public Object getArgument(String argName) {
        return getArguments().get(argName);
    }

    public void addToArgs(String argName, Object arg) {
        argsMap.put(argName, arg);
    }

    public List<Object> argsToList() {

        List<Object> argsList = new ArrayList<>();
        for(String key: argsMap.keySet()) {
            if(key.equals("signature")) {
                continue;
            }
            Object obj = argsMap.get(key);
            argsList.add(obj);
        }

        return argsList;
    }
}
