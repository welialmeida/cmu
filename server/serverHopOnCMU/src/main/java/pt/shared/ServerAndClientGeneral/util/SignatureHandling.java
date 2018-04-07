package pt.shared.ServerAndClientGeneral.util;

import pt.shared.ServerAndClientGeneral.Exceptions.SecException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.List;

public class SignatureHandling {

    public static byte[] createSignature(PrivateKey privKey, List data) throws InvalidKeyException {
        Signature dsaForSign = null;
        byte[] byteData = listToBytes(data);
        byte[] localSignature = null;

        try {
            dsaForSign = Signature.getInstance("SHA1withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (dsaForSign != null) {
            dsaForSign.initSign(privKey);
        }
        try {
            dsaForSign.update(byteData);
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        try {
            localSignature = dsaForSign.sign();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return localSignature;
    }

    public static boolean checkSignature(byte[] sign, PublicKey pubKey, List data) throws SecException {
        Signature dsaForVerify = null;
        byte[] byteData = listToBytes(data);
        boolean verifies = false;

        try {
            dsaForVerify = Signature.getInstance("SHA1withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            dsaForVerify.initVerify(pubKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            dsaForVerify.update(byteData);
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        try {
            verifies = dsaForVerify.verify(sign);
        } catch (SignatureException e) {
            e.printStackTrace();
            throw new SecException("signature verification failed SignatureHandling class");
        }
        if (!verifies)
            throw new SecException("signature not verified");

        return verifies;
    }

    private static byte[] listToBytes(List list) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        //DEBUG
        /*
        for (Object i : list) {
            System.out.println(i.toString());
        }
        */

        try {
            oos = new ObjectOutputStream(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (Object obj : list) {
                //System.out.println(obj);
                oos.writeObject(obj);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = bos.toByteArray();
        return bytes;
    }
}
