package pt.ulisboa.tecnico;

import org.junit.*;
import pt.shared.ServerAndClientGeneral.Exceptions.NonceErrorException;
import pt.shared.ServerAndClientGeneral.Exceptions.SecException;
import pt.shared.ServerAndClientGeneral.util.RSAKeyHandling;

import java.io.IOException;
import java.security.*;

import static org.junit.Assert.fail;


public class TestUnitServer {

    private static PublicKey pubK1;
    private static PublicKey pubK2;
    private static PublicKey pubKServer;
    private static PrivateKey privKey1;
    private static PrivateKey privKey2;
    private static KeyPair RSAkeys1;
    private static KeyPair RSAkeys2;
    private static String nonceRandomGenAlgorithm = "SHA1PRNG";

    private static KeyPair RSAkeys3;
    private static PublicKey pubK3;
    private static PrivateKey privKey3;

    private static double nonce;
    private static SecureRandom nonceGen;
    private static PrivateKey privKServer;


    @BeforeClass
    public static void beforeClass() throws GeneralSecurityException, IOException {
        System.out.println("\nSetup class");

        nonceGen = SecureRandom.getInstance(nonceRandomGenAlgorithm);
        privKServer = RSAKeyHandling.getPrivKey("server");
        pubKServer = RSAKeyHandling.getPubKey("server");

        RSAkeys1 = new RSAKeyGeneratorForTest().GenerateKey();
        pubK1 = RSAkeys1.getPublic();
        privKey1 = RSAkeys1.getPrivate();

        RSAkeys2 = new RSAKeyGeneratorForTest().GenerateKey();
        pubK2 = RSAkeys2.getPublic();
        privKey2 = RSAkeys2.getPrivate();

        RSAkeys3 = new RSAKeyGeneratorForTest().GenerateKey();
        pubK3 = RSAkeys3.getPublic();
        privKey3 = RSAkeys3.getPrivate();
    }

    @Before
    public void before() {

    }

    @Test
    public void simpleHelloNonceAndSignatureWorkingTest() {

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
