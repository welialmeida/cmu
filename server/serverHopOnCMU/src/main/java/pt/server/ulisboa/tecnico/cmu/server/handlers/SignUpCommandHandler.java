package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;
import pt.shared.ServerAndClientGeneral.Account;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.SignUpCommand;
import pt.shared.ServerAndClientGeneral.response.Error.ErrorResponse;
import pt.shared.ServerAndClientGeneral.response.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.util.List;
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
        PublicKey pubK = (PublicKey) cmd.getArgument("pubK");

        Account account = new Account(username, busTicketCode, pubK);
        List<Account> accList = super.getSignUpList();
        TreeMap<String, Object> argsMap = new TreeMap<>();

        for (Account acc : accList) {
            if(acc.getUsername().equals(account.getUsername()) || acc.getBusTicketCode().equals(account.getBusTicketCode())) {
                return new ErrorResponse(argsMap, "account username of busTicketCode already exist");
            }
        }

        super.addUsernameAndTicketCodeToPersistentStorage(username, busTicketCode, pubK);

        return new ErrorResponse(argsMap, "nothing returned in SignUp");
    }
}
