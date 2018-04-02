package pt.ulisboa.tecnico.cmu.response;

public class DownloadQuizQuestionsResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public DownloadQuizQuestionsResponse() {
        this.message = "download Quiz Question Response";
    }

    public String getMessage() {
        return this.message;
    }
}
