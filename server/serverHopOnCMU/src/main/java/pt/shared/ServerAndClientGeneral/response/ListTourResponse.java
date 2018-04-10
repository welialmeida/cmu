package pt.shared.ServerAndClientGeneral.response;

import java.util.TreeMap;

public class ListTourResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;
    private String Id = "ListTourResponse";

    public ListTourResponse(TreeMap<String, Object> argsMap) {
        super(argsMap);
        this.message = "ListTourResponse";
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
