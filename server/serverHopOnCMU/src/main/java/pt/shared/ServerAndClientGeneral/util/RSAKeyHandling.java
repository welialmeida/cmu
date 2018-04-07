package pt.shared.ServerAndClientGeneral.util;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.SecretKeySpec;

public class RSAKeyHandling {

    public static void write(String keyPath) throws GeneralSecurityException, IOException {
        // get an AES private key
        System.out.println("Generating RSA key ...");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keys = keyGen.generateKeyPair();
        System.out.println("Finish generating RSA keys");

        System.out.println("Private Key:");
        PrivateKey privKey = keys.getPrivate();
        byte[] privKeyEncoded = privKey.getEncoded();
        System.out.println(printHexBinary(privKeyEncoded));
        System.out.println("Public Key:");
        PublicKey pubKey = keys.getPublic();
        byte[] pubKeyEncoded = pubKey.getEncoded();
        System.out.println(printHexBinary(pubKeyEncoded));

        System.out.println("Writing Private key to '" + "/keys/"+keyPath + "' ...");
        FileOutputStream privFos = new FileOutputStream("./keys/"+keyPath + "-priv.gen");
        privFos.write(privKeyEncoded);
        privFos.close();
        System.out.println("Writing Pubic key to '" + "/keys/"+keyPath + "' ...");
        FileOutputStream pubFos = new FileOutputStream("./keys/"+keyPath + "-pub.gen");
        pubFos.write(pubKeyEncoded);
        pubFos.close();
    }

    public static Key read(String keyPath) throws GeneralSecurityException, IOException {
        System.out.println("Reading key from file " + keyPath + " ...");
        FileInputStream fis = new FileInputStream("./keys/"+keyPath);
        byte[] encoded = new byte[fis.available()];
        fis.read(encoded);
        fis.close();

        return new SecretKeySpec(encoded, "RSA");
    }

    public static byte[] readEncodedKey(String keyPath) throws GeneralSecurityException, IOException {
        //System.out.println("Reading key from file " + keyPath + " ...");
        FileInputStream fis = new FileInputStream(keyPath);
        byte[] encoded = new byte[fis.available()];
        fis.read(encoded);
        fis.close();

        return encoded;
    }

    public static PublicKey getPuvKey(String filename) throws GeneralSecurityException, IOException {
        byte[] keyBytes = RSAKeyHandling.readEncodedKey("./keys/"+filename+"-pub.gen");

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);

    }

    public static PrivateKey getPrivKey(String filename) throws GeneralSecurityException, IOException {
        byte[] keyBytes = RSAKeyHandling.readEncodedKey("./keys/"+filename+"-priv.gen");

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);

    }

}

