package pt.client.mobileClientDummy;

import org.junit.*;
import pt.shared.ServerAndClientGeneral.util.RSAKeyHandling;

import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.util.*;

import static junit.framework.TestCase.fail;

public class TestClientIT {
    private PublicKey pubKey = null;
    private PrivateKey privKey = null;
    private double nonce = 0;
    private static PublicKey serverPubKey;
    private List<Double> receivingNonceList = new ArrayList();
    private List<Double> sendingNonceList = new ArrayList();
    private static SecureRandom random;
    static Client client;
    private static Socket server;

    /*
    @BeforeClass
    public static void beforeClass() {
        String host = "localhost";
        String port = "9091";

        int serverPort = Integer.parseInt(port);
        String[] args = {host, port};
        try {
            Socket server = new Socket(host, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        client = new Client(server);
    }

    @Before
    public void before() {
        client.eraseDatabaseForTest();

        try {
            serverPubKey = RSAKeyHandling.getPuvKey("server");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void errorTesting() {

    }

    @Test
    public void fullFunctionalityTest() {

    }

    @After
    public void after() {
        client.eraseDatabaseForTest();
    }

    @AfterClass
    public static void afterClass() {
        // clear all
        //client.eraseDatabaseForTest();
    }
    */
}