package pt.ulisboa.tecnico;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RSAKeyGeneratorForTest {

    public  KeyPair GenerateKey() throws GeneralSecurityException, IOException {
        // get an AES private key
        System.out.println("Generating RSA key ..." );
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keys = keyGen.generateKeyPair();
        System.out.println("Finish generating RSA keys");

        return keys;
    }



}
