package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.TreeMap;

/**
 * Created by daniel on 01-04-2018.
 */

public class SignUpCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private static String message = "signUp";
    private static String Id = "SignUpCommand";

    @Override
    public String getId() {
        return Id;
    }

    public SignUpCommand(TreeMap<String, Object> argsMap, double sessionId, PrivateKey privKey,
                         PublicKey pubK, SecureRandom random) {

        super(argsMap, sessionId, privKey, pubK, random);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public String getMessage() {
        return this.message;
    }

    public String getUsername() {
        return (String) getArgument("username");
    }

    public String getBusTicket() {
        return (String) getArgument("busTicketCode");
    }
}