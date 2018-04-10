package pt.client.mobileClientDummy;

import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.ResponseHandler;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public abstract class ResponseHandlerImpl implements ResponseHandler {
    private static List<Double> nonceListReceive = new ArrayList();

    public void handle(Response response) {

        double nonce = (double) response.getArgument("nonce");
        PublicKey pubK = (PublicKey) response.getArgument("pubK");

        try {
            verifyNonce(nonce);
        } catch (NonceErrorException e) {
            e.printStackTrace();
        }

        try {
            verifySignature(pubK);
        } catch (SecException e) {
            e.printStackTrace();
        }

        return;
    }

    //TODO - nonce
    private void verifyNonce(double nonce) throws NonceErrorException{
        //verify with nonceLists


    }

    //TODO - signature
    private void verifySignature(PublicKey serverPubK) throws SecException{

    }
}
