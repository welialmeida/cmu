package pt.shared.ServerAndClientGeneral.response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.List;
import java.util.TreeMap;

public class DownloadQuizQuestionsResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private static String Id = "DownloadQuizQuestionsResponse";

    public DownloadQuizQuestionsResponse(TreeMap<String, Object> argsMap, Double sessionId, PrivateKey privKey,
                                         PublicKey pubK, SecureRandom random) {

        super(argsMap, sessionId, privKey, pubK, random);
    }

    @Override
    public void handle(ResponseHandler ch) {

        ch.handle(this);
    }

    public List<String> getQuestions() {
        return (List<String>) getArgument("questions");
    }

    public String toString() {
        String str = super.toString() + "\n";
        for(String question : getQuestions()) {
            str += question + "\n";
        }
        return str;
    }

    @Override
    public String getId() {
        return this.Id;
    }
}