package pt.ulisboa.tecnico.cmu.response;

public class LogOutResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public LogOutResponse() {
        super();
        this.message = "LogOut success";
    }

    public String getMessage() {
        return this.message;
    }
}
