package pt.server.ulisboa.tecnico.cmu.server.ServerExceptions;

public class WrongSessionIdException extends Exception {
    public WrongSessionIdException (String message) {
        super(message);
    }
}
