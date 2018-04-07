package pt.shared.ServerAndClientGeneral.response;

public class DownloadQuizQuestionsResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public DownloadQuizQuestionsResponse() {
        this.message = "download Quiz Question Response";
    }

    @Override
    public void handle(ResponseHandler ch) {
        return;
    }

    public String getMessage() {
        return this.message;
    }
}
