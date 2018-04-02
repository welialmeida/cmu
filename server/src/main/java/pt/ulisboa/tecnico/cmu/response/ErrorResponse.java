package pt.ulisboa.tecnico.cmu.response;

public class ErrorResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
