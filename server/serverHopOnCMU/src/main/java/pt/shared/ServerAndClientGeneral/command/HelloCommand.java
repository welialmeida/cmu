package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.response.Response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.TreeMap;

public class HelloCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String message;
    private String Id = "HelloCommand";

    @Override
    public String getId() {
        return Id;
    }

    public HelloCommand(String message, TreeMap<String, Object> argsMap, PrivateKey privKey, PublicKey pubK) {
        super(argsMap, privKey, pubK);
        this.message = message+"\n";
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public String getMessage() {
        return this.message;
    }
}
