package pt.client.mobileClientDummy;

import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.ResponseHandler;

public abstract class ResponseHandlerImpl implements ResponseHandler {
    public void handle(Response response) {
        //TODO deal with nonce and signature
    }
}
