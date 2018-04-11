package pt.shared.ServerAndClientGeneral.response.Error;

import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.ResponseHandler;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

public class ErrorResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private static String Id = "ErrorResponse";

    public ErrorResponse(TreeMap<String, Object> argsMap, PrivateKey privKey,
                         PublicKey pubK, SecureRandom random) {

        super(argsMap, privKey, pubK, random);
    }

    @Override
    public String getId() {
        return this.Id;
    }

    @Override
    public void handle(ResponseHandler ch) {
        ch.handle(this);
    }
}
