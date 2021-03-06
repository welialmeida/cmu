package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.response.Response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

public class ReadQuizResultsCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private static String Id = "ReadQuizResultsCommand";

    @Override
    public String getId() {
        return Id;
    }

    public ReadQuizResultsCommand(TreeMap<String, Object> argsMap, Double sessionId, PrivateKey privKey,
                                  PublicKey pubK, SecureRandom random) {

        super(argsMap, sessionId, privKey, pubK, random);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

}