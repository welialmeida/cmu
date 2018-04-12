package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;
import pt.server.ulisboa.tecnico.cmu.server.Persistence;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.AccountNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.PublicKeyNotFoundException;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.SignInCommand;
import pt.shared.ServerAndClientGeneral.response.Error.ErrorResponse;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.SignInResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.TreeMap;

public class SignInCommandHandler extends CommandHandlerImpl {

    public SignInCommandHandler() throws GeneralSecurityException, IOException {
        super();
    }

    public Response handle(Command cmd) {
        Response superRes = super.handle(cmd);
        if(superRes != null) {
            return superRes;
        }
        String username = ((SignInCommand)cmd).getUsername();
        String busTicketCode = ((SignInCommand)cmd).getBusTicket();
        String errorMsg = "account non existing";
        TreeMap<String, Object> argsMap = new TreeMap<>();
        Account databaseAccount;

        try {
            databaseAccount = getAndCheckAccount(cmd, username, busTicketCode);
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
        }

        if(!(databaseAccount.getSessionId() == null)) {
            errorMsg = "account already signed in";
            argsMap.put("return", errorMsg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

        double sessionId = genSessionId();
        activateAccount(databaseAccount, sessionId);
        argsMap.put("return", "Login Successful");
        return new SignInResponse(argsMap, sessionId, getPrivKey(), getPubKey(), getRandom());
    }

    public Double genSessionId() {
        return getRandom().nextDouble();
    }

    public void activateAccount(Account acc, Double sessionId) {
        acc.setSessionId(sessionId);
    }
}
