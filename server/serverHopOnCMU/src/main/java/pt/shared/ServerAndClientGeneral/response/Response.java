package pt.shared.ServerAndClientGeneral.response;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.TreeMap;

public abstract class Response implements Serializable {

    //TODO signature and nonce
    private TreeMap<String, Object> argsMap;
    private byte[] signature;
    private double nonce;
    private double sessionId;
    private PublicKey pubK;

    //TODO - nonce
    //TODO - signature
    public Response(double sessionId, TreeMap<String, Object> argsMap, PrivateKey serverPrivKey, PublicKey pubK) {
        this.argsMap = argsMap;
        this.argsMap.put("id", this.getId());
        setSessionId(sessionId);
        this.nonce = genNonce();
        this.argsMap.put("nonce", this.nonce);
        this.pubK = pubK;
        this.argsMap.put("pubK", this.pubK);
        this.signature = genSignature(serverPrivKey);
    }

    public Response(TreeMap<String, Object> argsMap, PrivateKey serverPrivKey, PublicKey pubK) {
        this.argsMap = argsMap;
        this.argsMap.put("id", this.getId());
        this.nonce = genNonce();
        this.argsMap.put("nonce", this.nonce);
        this.pubK = pubK;
        this.argsMap.put("pubK", this.pubK);
        this.signature = genSignature(serverPrivKey);
    }

    public Response(TreeMap<String, Object> argsMap) {
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

        Response rsp = (Response)obj;
        for (String key : rsp.getArguments().keySet()) {
            if (this.getArguments().containsKey(key)) {
                if(!this.getArguments().containsValue(rsp.getArgument(key))) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public abstract void handle(ResponseHandler ch);

    public abstract String getMessage();

    public abstract String getId();

    private void setSessionId(double sessionId) {
        this.sessionId = sessionId;
        addToArgs("sessionId", sessionId);
    }

    private double getSessionId(String busTicketCode) {
        return this.sessionId;
    }

    public TreeMap<String, Object> getArguments() {
        return this.argsMap;
    }

    //TODO - nonce
    public double genNonce() {
        //Secure random.nextDouble
        //
        return 0.0;
    }

    public double getNonce() {
        return this.nonce;
    }

    //TODO - signature
    public byte[] genSignature(PrivateKey privKey) {
        //List args = argsMapToList();
        //Signature signature = SignatureHandling.createSignature(privKey, );
        //addToArgs("signature", signature) //add to argsMap
        //return signature;
        return null;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    //TODO - signature
    public List argsMapToList() {
        return null;
    }

    public Object getArgument(String argName) {
        return argsMap.get(argName);
    }

    public void addToArgs(String argName, Object arg) {
        argsMap.put(argName, arg);
    }
}
