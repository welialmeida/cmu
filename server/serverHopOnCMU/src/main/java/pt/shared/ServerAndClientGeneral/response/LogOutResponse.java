package pt.shared.ServerAndClientGeneral.response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

public class LogOutResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private static String Id = "LogOutResponse";

    public LogOutResponse(TreeMap<String, Object> argsMap, double sessionId, PrivateKey privKey,
                          PublicKey pubK, SecureRandom random) {

        super(argsMap, sessionId, privKey, pubK, random);
    }

    @Override
    public void handle(ResponseHandler ch) {

        ch.handle(this);
    }

    @Override
    public String getId() {
        return this.Id;
    }
}
