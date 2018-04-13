package pt.shared.ServerAndClientGeneral.command;

import pt.server.ulisboa.tecnico.cmu.server.handlers.HelloCommandHandler;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

public class HelloCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private static String message = "Hello from server";
    private static String Id = "HelloCommand";

    @Override
    public String getId() {
        return Id;
    }

    public HelloCommand(TreeMap<String, Object> argsMap, PrivateKey privKey,
                        PublicKey pubK, SecureRandom random) {

        super(argsMap, privKey, pubK, random);
        this.message = message+"\n";
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public String toString() {
        return getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
