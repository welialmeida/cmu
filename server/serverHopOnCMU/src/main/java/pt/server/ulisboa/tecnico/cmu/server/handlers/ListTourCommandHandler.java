package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.AccountNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.PublicKeyNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.WrongSessionIdException;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.ListTourLocationsCommand;
import pt.shared.ServerAndClientGeneral.response.Error.ErrorResponse;
import pt.shared.ServerAndClientGeneral.response.ListTourResponse;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.TreeMap;

public class ListTourCommandHandler extends CommandHandlerImpl {

    public ListTourCommandHandler() throws GeneralSecurityException, IOException {
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

        try {
            databaseAccount = getAndCheckAccount(cmd, username, busTicketCode, sessionId);
        } catch (IOException e) {
            e.printStackTrace();
            argsMap.put("return", errorMsg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            argsMap.put("return", errorMsg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            argsMap.put("return", e.getMessage());
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        } catch (PublicKeyNotFoundException e) {
            e.printStackTrace();
            argsMap.put("return", e.getMessage());
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        } catch (WrongSessionIdException e) {
            e.printStackTrace();
            errorMsg = "wrong sessionId";
            argsMap.put("return", errorMsg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }


        System.out.println(getTourLocations());
        String msg = "success listing tours";
        argsMap.put("return", msg);
        argsMap.put("locations", new ArrayList(getTourLocations()));
        return new ListTourResponse(argsMap, sessionId, getPrivKey(), getPubKey(), getRandom());
    }
}
