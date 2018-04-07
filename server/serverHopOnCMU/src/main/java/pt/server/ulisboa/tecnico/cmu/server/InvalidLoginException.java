package pt.server.ulisboa.tecnico.cmu.server;

public class InvalidLoginException extends Exception {

    public InvalidLoginException(String message) {
        super(message);
    }
}
