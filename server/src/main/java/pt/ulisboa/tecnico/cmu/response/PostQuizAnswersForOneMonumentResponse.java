package pt.ulisboa.tecnico.cmu.response;

public class PostQuizAnswersForOneMonumentResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public PostQuizAnswersForOneMonumentResponse() {
        this.message = "PostQuizAnswersResponse";
    }

    public String getMessage() {
        return this.message;
    }
}
