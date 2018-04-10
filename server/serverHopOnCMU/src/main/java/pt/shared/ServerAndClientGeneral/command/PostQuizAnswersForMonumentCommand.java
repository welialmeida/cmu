package pt.shared.ServerAndClientGeneral.command;

import pt.shared.ServerAndClientGeneral.response.Response;

import java.util.TreeMap;

/**
 * Created by daniel on 01-04-2018.
 */

public class PostQuizAnswersForMonumentCommand extends Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String message;
    private String Id = "PostQuizAnswersForMonumentCommand";

    @Override
    public String getId() {
        return Id;
    }

    public PostQuizAnswersForMonumentCommand(String message, TreeMap<String, Object> argsMap) {
        super(argsMap);
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