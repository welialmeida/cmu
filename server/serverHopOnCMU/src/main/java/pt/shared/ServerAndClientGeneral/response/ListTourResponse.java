package pt.shared.ServerAndClientGeneral.response;

public class ListTourResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public ListTourResponse() {
        this.message = "ListTourResponse";
    }

    @Override
    public void handle(ResponseHandler ch) {
        return;
    }

    public String getMessage() {
        return this.message;
    }
}
