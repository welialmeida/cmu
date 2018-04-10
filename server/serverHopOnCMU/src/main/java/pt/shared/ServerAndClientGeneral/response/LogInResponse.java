package pt.shared.ServerAndClientGeneral.response;

import java.util.TreeMap;

public class LogInResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;
    private double uniqueId;
    private String Id = "LogInResponse";

    public LogInResponse(double uniqueId, TreeMap<String, Object> argsMap) {
        super(argsMap);
        this.message = "LogIn success";
        this.uniqueId = uniqueId;
    }

    public double getUniqueId() {
        return this.uniqueId;
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
