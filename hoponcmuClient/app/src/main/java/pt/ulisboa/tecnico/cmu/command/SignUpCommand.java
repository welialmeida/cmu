package pt.ulisboa.tecnico.cmu.command;

import pt.ulisboa.tecnico.cmu.response.Response;

/**
 * Created by daniel on 01-04-2018.
 */

public class SignUpCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String message;
    private String Id = "SignUpCommand";

    @Override
    public String getId() {
        return Id;
    }

    public SignUpCommand(String message) {
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