package pt.shared.ServerAndClientGeneral.response;

public class ListTourResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public ListTourResponse() {
        this.message = "ListTourResponse";
    }

    @Override
    public Response handle(ResponseHandler ch) {
        return null;
    }

    public String getMessage() {
        return this.message;
    }
}
