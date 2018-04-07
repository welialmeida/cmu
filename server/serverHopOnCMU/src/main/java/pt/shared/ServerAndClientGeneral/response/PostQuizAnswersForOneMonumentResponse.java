package pt.shared.ServerAndClientGeneral.response;

public class PostQuizAnswersForOneMonumentResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;

    public PostQuizAnswersForOneMonumentResponse() {
        this.message = "PostQuizAnswersResponse";
    }

    @Override
    public void handle(ResponseHandler ch) {
        return;
    }

    public String getMessage() {
        return this.message;
    }
}
