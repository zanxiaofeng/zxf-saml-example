package com.zxf.example.utils;

import com.zxf.example.service.SamlResponseBuilder;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.CredentialSupport;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CredentialUtil {
    public static BasicCredential getCredential() throws Exception {
        return CredentialSupport.getSimpleCredential(loadPublicKey(), loadPrivateKey());
    }

    private static RSAPublicKey loadPublicKey() throws Exception {
        URL publicKeyURL = CredentialUtil.class.getClassLoader().getResource("keys/public-key");
        // Please note "Files.readAllBytes(Paths.get(publicKeyURL.toURI()))" will fail when run this program by jar
        byte[] encodedPublicKey = Files.readAllBytes(Paths.get(publicKeyURL.toURI()));
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedPublicKey));
    }

    private static RSAPrivateKey loadPrivateKey() throws Exception {
        URL privateKeyURL = CredentialUtil.class.getClassLoader().getResource("keys/private-key");
        // Please note "Files.readAllBytes(Paths.get(privateKeyURL.toURI()))" will fail when run this program by jar
        byte[] encodedPrivateKey = Files.readAllBytes(Paths.get(privateKeyURL.toURI()));
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(encodedPrivateKey));
    }
}
