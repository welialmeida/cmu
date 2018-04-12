package pt.shared.ServerAndClientGeneral.response;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class ListTourResponse extends Response {

    private static final long serialVersionUID = 734457624276534179L;
    private static String Id = "ListTourResponse";

    public ListTourResponse(TreeMap<String, Object> argsMap, Double sessionId, PrivateKey privKey,
                            PublicKey pubK, SecureRandom random) {

        super(argsMap, sessionId, privKey, pubK, random);
    }

    @Override
    public void handle(ResponseHandler ch) {
        ch.handle(this);
    }

    @Override
    public String toString() {
        String locs = "";
        for (String loc : getLocations()) {
            locs += loc + "\n";
        }
        return locs;
    }

    public List<String> getLocations() {
        return new ArrayList<String>((Collection<String>) getArgument("locations"));
    }


    @Override
    public String getId() {
        return this.Id;
    }
}
