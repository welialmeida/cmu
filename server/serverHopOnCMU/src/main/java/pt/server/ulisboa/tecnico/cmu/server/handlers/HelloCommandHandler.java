package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.response.HelloResponse;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.TreeMap;

public class HelloCommandHandler extends CommandHandlerImpl {

    public HelloCommandHandler() throws GeneralSecurityException, IOException {
        super();
    }

    public Response handle(Command cmd){
        Response superRes = super.handle(cmd);
        if(superRes != null) {
            return superRes;
        }

        System.out.println(cmd.getMessage());
        TreeMap<String, Object> argsMap = new TreeMap();
        return new HelloResponse(argsMap);
    }
}
