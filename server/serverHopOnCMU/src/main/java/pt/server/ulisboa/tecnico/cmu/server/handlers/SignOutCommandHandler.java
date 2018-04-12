package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.AccountNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.PublicKeyNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.WrongSessionIdException;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.SignInCommand;
import pt.shared.ServerAndClientGeneral.command.SignOutCommand;
import pt.shared.ServerAndClientGeneral.response.Error.ErrorResponse;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.SignOutResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.TreeMap;

public class SignOutCommandHandler extends CommandHandlerImpl {
    public SignOutCommandHandler() throws GeneralSecurityException, IOException {
        super();
    }

    public Response handle(Command cmd) {
        Response superRes = super.handle(cmd);
        if(superRes != null) {
            return superRes;
        }
        String username = ((SignOutCommand)cmd).getUsername();
        String busTicketCode = ((SignOutCommand)cmd).getBusTicket();
        Double sessionId = ((SignOutCommand)cmd).getSessionId();
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

        if((databaseAccount.getSessionId() == null)) {
            errorMsg = "account not signed in";
            argsMap.put("return", errorMsg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

        if(sessionId != null && databaseAccount.getSessionId() != null &&
                databaseAccount.getSessionId().equals(sessionId)) {
            deactivateAccount(databaseAccount);
            argsMap.put("return", "SignOut successful");
            return new SignOutResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }
        errorMsg = "wrong sessionId";
        argsMap.put("return", errorMsg);
        return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
    }

    public void deactivateAccount(Account acc) {
        acc.setSessionId(null);
    }
}
