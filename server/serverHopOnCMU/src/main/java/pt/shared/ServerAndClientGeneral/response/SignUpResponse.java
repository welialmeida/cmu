package pt.shared.ServerAndClientGeneral.response;

import java.util.TreeMap;

public class SignUpResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;
    private String Id = "SignUpResponse";


    public SignUpResponse(TreeMap<String, Object> argsMap) {
        super(argsMap);
        this.message = "SignUp Success";
    }

    @Override
    public void handle(ResponseHandler ch) {
        ch.handle(this);
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String getId() {
        return this.Id;
    }
}
