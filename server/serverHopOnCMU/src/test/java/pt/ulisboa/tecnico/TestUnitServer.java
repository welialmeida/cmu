package pt.ulisboa.tecnico;

import org.junit.*;
import pt.server.ulisboa.tecnico.cmu.server.handlers.SignUpCommandHandler;
import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.command.Command;
import pt.shared.ServerAndClientGeneral.command.SignUpCommand;
import pt.shared.ServerAndClientGeneral.response.LogOutResponse;
import pt.shared.ServerAndClientGeneral.response.Response;
import pt.shared.ServerAndClientGeneral.response.SignUpResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.fail;


public class TestUnitServer {

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {

    }

    //TODO signup test
    @Test
    public void SimpleSignUp()
            throws SecException, NonceErrorException, GeneralSecurityException, IOException {
        /*
        String username = "user";
        String busTicketCode = "code";

        Response dummyResponse = new SignUpResponse();
        Command signUpCommand = new SignUpCommand();
        SignUpCommandHandler handler = null;

        Response outputResponse = null;

        handler = new SignUpCommandHandler();

        outputResponse = handler.handle(signUpCommand);

        if(!outputResponse.equals(dummyResponse)) {
            fail();
        }
        */

    }

    //TODO signup test
    @Test
    public void SignUpDuplicateUsername() {

    }

    //TODO signup test
    @Test
    public void SignUpDuplicateTicket() {

    }

    //TODO login test
    @Test
    public void duplicateSessionIdLogin() {

    }

    //TODO login test
    @Test
    public void duplicateUsernameIdLogin() {

    }

    //TODO login test
    @Test
    public void duplicateTicketIdLogin() {

    }

    //TODO logOut test
    @Test
    public void duplicateLogOut() {

    }

    //TODO signature
    @Test
    public void signatureLogin() {

    }

    //TODO nonce
    @Test
    public void nonceLogin() {

    }

    @After
    public void after() {

    }

    @AfterClass
    public static void afterClass() {

    }

}
