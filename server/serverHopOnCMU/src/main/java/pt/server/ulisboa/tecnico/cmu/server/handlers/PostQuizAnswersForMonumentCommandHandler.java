package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.ListTourLocationsCommand;
import pt.shared.ServerAndClientGeneral.response.ListTourResponse;
import pt.shared.ServerAndClientGeneral.response.PostQuizAnswersForOneMonumentResponse;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.TreeMap;

public class PostQuizAnswersForMonumentCommandHandler extends CommandHandlerImpl {

    public PostQuizAnswersForMonumentCommandHandler() throws GeneralSecurityException, IOException {
        super();
    }

    public Response handle(Command cmd) {
        Response superRes = super.handle(cmd);
        if (superRes != null) {
            return superRes;
        }

        String username = ((ListTourLocationsCommand)cmd).getUsername();
        String busTicketCode = ((ListTourLocationsCommand)cmd).getBusTicket();
        Double sessionId = cmd.getSessionId();
        String errorMsg = "account non existing";
        TreeMap<String, Object> argsMap = new TreeMap<>();
        Account databaseAccount;


        System.out.println(getTourLocations());
        String msg = "success listing tours";
        argsMap.put("return", msg);

        return new PostQuizAnswersForOneMonumentResponse(argsMap, sessionId, getPrivKey(), getPubKey(), getRandom());
    }
}
