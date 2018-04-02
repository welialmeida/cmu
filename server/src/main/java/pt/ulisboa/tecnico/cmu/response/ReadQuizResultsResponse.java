package pt.ulisboa.tecnico.cmu.response;

public class ReadQuizResultsResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public ReadQuizResultsResponse() {
        this.message = "ReadQuizResultsResponse";
    }

    public String getMessage() {
        return this.message;
    }
}
