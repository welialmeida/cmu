package pt.shared.ServerAndClientGeneral.response;

import java.util.TreeMap;

public class DownloadQuizQuestionsResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private String message;
    private String Id = "DownloadQuizQuestionsResponse";

    public DownloadQuizQuestionsResponse(TreeMap<String, Object> argsMap) {
        super(argsMap);
        this.message = "download Quiz Question Response";
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
