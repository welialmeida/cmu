package pt.client.mobileClientDummy.handlers;

import pt.client.mobileClientDummy.ResponseHandlerImpl;
import pt.shared.ServerAndClientGeneral.response.Response;

public class HelloClientHandlerImpl extends ResponseHandlerImpl {
    public void handle(Response rsp) {
        super.handle(rsp);
        System.out.println(rsp);
    }
}
