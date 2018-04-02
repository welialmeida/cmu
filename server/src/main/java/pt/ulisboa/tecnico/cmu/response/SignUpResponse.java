package pt.ulisboa.tecnico.cmu.response;

public class SignUpResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public SignUpResponse() {
        this.message = "SignUp Success";
    }

    public String getMessage() {
        return this.message;
    }
}
