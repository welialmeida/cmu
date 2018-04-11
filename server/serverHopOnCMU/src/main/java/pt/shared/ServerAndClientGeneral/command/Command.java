package pt.shared.ServerAndClientGeneral.command;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

import pt.shared.ServerAndClientGeneral.Proto;
import pt.shared.ServerAndClientGeneral.response.Response;

public abstract class Command extends Proto {

    public abstract Response handle(CommandHandler ch);

    public abstract String getId();

    public Command(TreeMap<String, Object> argsMap, double sessionId, PrivateKey clientPrivKey,
                   PublicKey pubK, SecureRandom random) {
        super(argsMap, sessionId, clientPrivKey, pubK, random);
    }

    public Command(TreeMap<String, Object> argsMap, PrivateKey clientPrivKey, PublicKey pubK, SecureRandom random) {
        super(argsMap, clientPrivKey, pubK, random);
    }

}
