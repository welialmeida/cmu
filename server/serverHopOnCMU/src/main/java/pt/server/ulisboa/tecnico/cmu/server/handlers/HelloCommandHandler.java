package pt.server.ulisboa.tecnico.cmu.server.handlers;

import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.response.HelloResponse;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.server.ulisboa.tecnico.cmu.server.CommandHandlerImpl;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class HelloCommandHandler extends CommandHandlerImpl {

    public HelloCommandHandler() throws GeneralSecurityException, IOException {
        super();
    }

    public Response handle(Command command) {
        super.handle(command);
        System.out.println(command.getMessage());
        return new HelloResponse();
    }
}
