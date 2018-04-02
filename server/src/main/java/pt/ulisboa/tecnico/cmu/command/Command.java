package pt.ulisboa.tecnico.cmu.command;

import java.io.Serializable;

import pt.ulisboa.tecnico.cmu.response.Response;

public abstract class Command implements Serializable {
    private String args = "";

    public abstract Response handle(CommandHandler ch);

    public abstract String getMessage();

    public abstract String getId();

    public String[] getArguments() {
        return this.args.split("|");
    }

    public void setArguments(String args) {
        this.args = args;
    }

    public void addArgument(String arg) {
        this.args += "|" + arg;
    }
}
