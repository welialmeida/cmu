package pt.shared.ServerAndClientGeneral.response;

public class ErrorResponse extends Response {

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

    @Override
    public void handle(ResponseHandler ch) {
        return;
    }
}
