package pt.shared.ServerAndClientGeneral.command;

import pt.server.ulisboa.tecnico.cmu.server.handlers.SignInCommandHandler;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

public class SignInCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private static String Id = "SignInCommand";

    @Override
    public String getId() {
        return Id;
    }

    public SignInCommand(TreeMap<String, Object> argsMap, PrivateKey privKey,
                         PublicKey pubK, SecureRandom random) {

        super(argsMap, privKey, pubK, random);
    }

    public String getUsername() {
        return ((TreeMap<String, String>) getArgument("return")).get("username");
    }

    public String getBusTicket() {
        return ((TreeMap<String, String>) getArgument("return")).get("busTicketCode");
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}