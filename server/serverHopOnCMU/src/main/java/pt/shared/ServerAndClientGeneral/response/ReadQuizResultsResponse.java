package pt.shared.ServerAndClientGeneral.response;

public class ReadQuizResultsResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public ReadQuizResultsResponse() {
        this.message = "ReadQuizResultsResponse";
    }

    @Override
    public Response handle(ResponseHandler ch) {
        return null;
    }

    public String getMessage() {
        return this.message;
    }
}
