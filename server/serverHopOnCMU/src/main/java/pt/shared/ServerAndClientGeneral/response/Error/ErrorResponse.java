package pt.shared.ServerAndClientGeneral.response.Error;

import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.ResponseHandler;

import java.util.TreeMap;

public class ErrorResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;
    private String Id = "ErrorResponse";

    public ErrorResponse(TreeMap<String, Object> argsMap, String message) {
        super(argsMap);
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String getId() {
        return this.Id;
    }

    @Override
    public void handle(ResponseHandler ch) {
        ch.handle(this);
    }
}
