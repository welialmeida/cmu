package pt.server.ulisboa.tecnico.cmu.server.ServerExceptions;

public class InvalidLoginException extends Exception {

    public InvalidLoginException(String message) {
        super(message);
    }
}
