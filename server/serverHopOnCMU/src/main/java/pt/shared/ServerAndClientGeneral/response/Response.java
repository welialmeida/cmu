package pt.shared.ServerAndClientGeneral.response;

import pt.shared.ServerAndClientGeneral.Proto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

public abstract class Response extends Proto {

    public abstract void handle(ResponseHandler ch);

    public abstract String getId();

    public Response(TreeMap<String, Object> argsMap, Double sessionId, PrivateKey serverPrivKey, PublicKey pubK,
                    SecureRandom random) {
        super(argsMap, sessionId, serverPrivKey, pubK, random);
    }

    public Response(TreeMap<String, Object> argsMap, PrivateKey serverPrivKey, PublicKey pubK, SecureRandom random) {
        super(argsMap, serverPrivKey, pubK, random);
    }
}
