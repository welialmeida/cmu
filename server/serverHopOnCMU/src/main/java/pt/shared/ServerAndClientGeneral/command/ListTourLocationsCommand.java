package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.response.Response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Created by daniel on 01-04-2018.
 */

public class ListTourLocationsCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private static String Id = "ListTourLocationsCommand";

    @Override
    public String getId() {
        return Id;
    }

    public ListTourLocationsCommand(TreeMap<String, Object> argsMap, Double sessionId, PrivateKey privKey,
                                    PublicKey pubK, SecureRandom random) {

        super(argsMap, sessionId, privKey, pubK, random);
    }

    public Collection<String> getLocations() {
        return (Collection<String>)getArgument("locations");
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