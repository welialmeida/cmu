package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;
import pt.server.ulisboa.tecnico.cmu.server.Persistence;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.SignUpCommand;
import pt.shared.ServerAndClientGeneral.response.Error.ErrorResponse;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.SignUpResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.TreeMap;

public class SignUpCommandHandler extends CommandHandlerImpl {

    public SignUpCommandHandler() throws GeneralSecurityException, IOException {
        super();
    }

    public Response handle(Command cmd) {
        Response superRes = super.handle(cmd);
        if(superRes != null) {
            return superRes;
        }
        String username = ((SignUpCommand)cmd).getUsername();
        String busTicketCode = ((SignUpCommand)cmd).getBusTicket();
        PublicKey pubK = cmd.getPubK();

        TreeMap<String, Object> argsMap = new TreeMap<>();

        if(Persistence.exists(username, busTicketCode)) {
            String msg = "account username or busTicketCode already exist";
            argsMap.put("return", msg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

        addUsernameAndTicketCodeToPersistentStorage(username, busTicketCode, pubK);

        String msg = "successful signUp";
        argsMap.put("return", msg);
        return new SignUpResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
    }
}