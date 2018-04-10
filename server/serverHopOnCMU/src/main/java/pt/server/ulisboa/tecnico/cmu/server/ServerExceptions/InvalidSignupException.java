package pt.server.ulisboa.tecnico.cmu.server.ServerExceptions;

public class InvalidSignupException extends Exception {
    public InvalidSignupException (String message) {
        super(message);
    }
}
