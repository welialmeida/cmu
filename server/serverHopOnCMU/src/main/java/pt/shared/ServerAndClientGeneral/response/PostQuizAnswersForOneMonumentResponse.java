package pt.shared.ServerAndClientGeneral.response;

import java.util.TreeMap;

public class PostQuizAnswersForOneMonumentResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;
    private String Id = "PostQuizAnswersForOneMonumentResponse";

    public PostQuizAnswersForOneMonumentResponse(TreeMap<String, Object> argsMap) {
        super(argsMap);
        this.message = "PostQuizAnswersResponse";
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
