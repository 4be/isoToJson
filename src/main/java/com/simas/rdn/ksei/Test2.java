package com.simas.rdn.ksei;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KseiValidateAckApplication {

//    public static  void ewe(String filename, String algorithm) throws Exception {
//        File f = new File(filename);
//        FileInputStream fis = new FileInputStream(f);
//        DataInputStream dis = new DataInputStream(fis);
//        byte[] keyBytes = new byte[(int) f.length()];
//        dis.readFully(keyBytes);
//        dis.close();
//
//        String temp = new String(keyBytes);
//        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----\n", "");
//        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
//        System.out.println("temp key\n"+temp);
//        System.out.println("Private key\n"+privKeyPEM);
//
////	      Base64 b64 = new Base64();
//        byte [] decoded = Base64.getDecoder().decode(privKeyPEM.replace("\n", ""));
////	      byte [] decoded = b64.decode(privKeyPEM);
//
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
//        KeyFactory kf = KeyFactory.getInstance(algorithm);
//        return kf.generatePrivate(spec);
//    }


    public static PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {
        File f = new File(filename);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        String temp = new String(keyBytes);
        String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----\n", "");
        privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
        System.out.println("temp key\n" + temp);
        System.out.println("Private key\n" + privKeyPEM);

//	      Base64 b64 = new Base64();
        Base64.Encoder encoder = Base64.getUrlEncoder();
        // Encoding URL
        String eStr = encoder.encodeToString(temp.getBytes());
        System.out.println("Encoded result: " + eStr);
        // Getting decoder
        Base64.Decoder decoder = Base64.getUrlDecoder();
        // Decoding URl
        String dStr = new String(decoder.decode(eStr));
        byte[] eq = eStr.getBytes();
        System.out.println("Decoded result: " + dStr);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(eq);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(spec);
    }


    //    public static void verifyRSASHA256() {
//        FileInputStream fm;
//        try {
//            fm = new FileInputStream("C:\\Users\\janga\\Documents\\ApiGateway\\result.p12");
//            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//            try {
//                ks.load(fm, "Ap1B51M$$$".toCharArray());
//
//                Certificate cert = ks.getCertificate("1");
//                PublicKey pubKey = cert.getPublicKey();
//                PrivateKey keyp12 = (PrivateKey)ks.getKey("1", "Ap1B51M$$$".toCharArray());
//
//
//                String data = "00409659120038007618100000";
//                byte[] byteData = data.getBytes("UTF-8");
//                Signature signn = Signature.getInstance("SHA256withRSA");
//
//                signn.initSign(keyp12);
//                signn.update(byteData);
//                byte[] s = Base64.getDecoder().decode("mlTSgQ+j3xolbyVoynesi5STx9qBKbAO6C4AjLKZVJMB3FM2Uink9rtRHQzRf8ip3HZ6w2y1TJsltzhReR84O0SlyO4KFqkI2ModbH6VV5LKUGH0oizeYdD+tD9hu63jPPogxjncW5SQjrN0VuYkhxAh8LlalE8GgYCNCkrCrNhtizxZ48QVQ7y2fQgD+GjWptahMFruXRBa3BXRLayAYWAEBajfi2P6+kZ3VlVm9ENfutKljKlyniIXyFZgUhSy1UWosF+fXUqeC5bPaXBt6XsCk5teHoQaAOvm5YBqSXBn0CReWFcjpeRXQdqlh7ESsKc9j2DLqkZ+oA87SNTmBA==");
//                String encrypted = Base64.getEncoder().encodeToString(s);
//                System.out.println("encrypted\n"+encrypted);
//
//                signn.initSign(keyp12);
//                signn.update(byteData);
//                byte[] sp12 = signn.sign();
//                String encryptedp12 = Base64.getEncoder().encodeToString(sp12);
//                System.out.println("encryptedp12\n"+encryptedp12);
//
//                signn.initVerify(pubKey);
//                signn.update(byteData);
//                System.out.println(signn.verify(s));
//
//
//            } catch (Exception e) {
//                System.out.println( e );
//                e.printStackTrace();
//            }
//
//        } catch (KeyStoreException | FileNotFoundException e1) {
//            e1.printStackTrace();
//        }
//    }
//

    public static void main(String[] args) throws Exception { // Getting encoder
        getPemPrivateKey("C:\\Users\\janga\\Documents\\ApiGateway\\private.pem", "RSA");

    }


}
