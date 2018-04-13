package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;
import pt.server.ulisboa.tecnico.cmu.server.LocationNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.AccountNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.PublicKeyNotFoundException;
import pt.server.ulisboa.tecnico.cmu.server.ServerExceptions.WrongSessionIdException;
import pt.server.ulisboa.tecnico.cmu.server.SsidNotFoundException;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.DownloadQuizQuestionsCommand;
import pt.shared.ServerAndClientGeneral.response.DownloadQuizQuestionsResponse;
import pt.shared.ServerAndClientGeneral.response.Error.ErrorResponse;
import pt.shared.ServerAndClientGeneral.response.ListTourResponse;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.TreeMap;

public class DownloadQuizQuestionsResultsHandler extends CommandHandlerImpl {

    public DownloadQuizQuestionsResultsHandler() throws GeneralSecurityException, IOException {
        super();
    }

    public Response handle(Command cmd) {
        Response superRes = super.handle(cmd);
        if (superRes != null) {
            return superRes;
        }

        String username = ((DownloadQuizQuestionsCommand) cmd).getUsername();
        String busTicketCode = ((DownloadQuizQuestionsCommand) cmd).getBusTicket();
        Double sessionId = cmd.getSessionId();
        String errorMsg = "account non existing";
        TreeMap<String, Object> argsMap = new TreeMap<>();
        Account databaseAccount;

        String ssid = ((DownloadQuizQuestionsCommand) cmd).getSsid();

        String location = null;
        try {
            location = findLocationBySsid(ssid);
        } catch (SsidNotFoundException e) {
            e.printStackTrace();
            argsMap.put("return", e.getMessage());
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

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

        if(!getTourLocations().contains(location)) {
            errorMsg = "no location found";
            argsMap.put("return", errorMsg);
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

        List<String> retQuestions = null;
        try {
            retQuestions = getQuestions(location, ssid);
        } catch (SsidNotFoundException e) {
            e.printStackTrace();
            argsMap.put("return", e.getMessage());
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        } catch (LocationNotFoundException e) {
            e.printStackTrace();
            argsMap.put("return", e.getMessage());
            return new ErrorResponse(argsMap, getPrivKey(), getPubKey(), getRandom());
        }

        System.out.println();
        String msg = "success downloading questions";
        argsMap.put("return", msg);
        argsMap.put("questions", retQuestions);
        return new DownloadQuizQuestionsResponse(argsMap, sessionId, getPrivKey(), getPubKey(), getRandom());
    }
}
