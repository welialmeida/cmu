package pt.shared.ServerAndClientGeneral.response;

public class SignUpResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public SignUpResponse() {
        this.message = "SignUp Success";
    }

    @Override
    public Response handle(ResponseHandler ch) {
        return null;
    }

    public String getMessage() {
        return this.message;
    }
}
