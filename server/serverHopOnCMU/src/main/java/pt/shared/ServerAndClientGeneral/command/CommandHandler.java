package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.response.Response;

public interface CommandHandler {
    Response handle(Command hc);
}
