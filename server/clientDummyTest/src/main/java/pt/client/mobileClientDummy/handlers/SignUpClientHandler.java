package pt.client.mobileClientDummy.handlers;

import pt.client.mobileClientDummy.ResponseHandlerImpl;
import pt.shared.ServerAndClientGeneral.response.Response;

public class SignUpClientHandler extends ResponseHandlerImpl{
    public void handle(Response rsp) {
        super.handle(rsp);
        System.out.println();
        System.out.println(rsp);
    }
}