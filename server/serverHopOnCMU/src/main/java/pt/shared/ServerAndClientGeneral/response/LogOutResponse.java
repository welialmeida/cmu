package pt.shared.ServerAndClientGeneral.response;

public class LogOutResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public LogOutResponse() {
        super();
        this.message = "LogOut success";
    }

    @Override
    public void handle(ResponseHandler ch) {
        return;
    }

    public String getMessage() {
        return this.message;
    }
}
