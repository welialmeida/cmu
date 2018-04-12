package pt.client.mobileClientDummy;

import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.ResponseHandler;
import pt.shared.ServerAndClientGeneral.util.SignatureHandling;

import java.util.ArrayList;
import java.util.List;

public abstract class ResponseHandlerImpl implements ResponseHandler {

    private static List<Double> nonceListsReceived = new ArrayList();
    private static Double sessionId = null;

    public void handle(Response response) {

        try {
            verifyNonce(response);
        } catch (NonceErrorException e) {
            e.printStackTrace();
        }

        try {
            verifySignature(response);
        } catch (SecException e) {
            e.printStackTrace();
        }

        return;
    }

    private void verifyNonce(Response rsp) throws NonceErrorException {
        //verify with nonceLists
        double nonce = rsp.getNonce();
        if(nonceListsReceived.contains(nonce)) {
            throw new NonceErrorException("nonce check failed");
        } else {
            nonceListsReceived.add(nonce);
        }
    }

    private void verifySignature(Response rsp) throws SecException {
        SignatureHandling.checkSignature(rsp.getSignature(), rsp.getPubK(), rsp.argsToList());
    }

    public static void setSessionId(Double sesId) {
        sessionId = sesId;
    }

    public static Double getSessionId() {
        return sessionId;
    }
}
