package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.response.Response;

/**
 * Created by daniel on 01-04-2018.
 */

public class LoginCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String message;
    private String Id = "LoginCommand";

    @Override
    public String getId() {
        return Id;
    }

    public LoginCommand(String message) {
        this.message = message;
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public String getMessage() {
        return this.message;
    }
}