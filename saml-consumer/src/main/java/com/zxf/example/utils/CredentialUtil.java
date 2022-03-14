package com.zxf.example.utils;

import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.CredentialSupport;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

public class CredentialUtil {
    public static BasicCredential getCredential() throws Exception {
        return CredentialSupport.getSimpleCredential(loadPublicKey(), null);
    }

    private static RSAPublicKey loadPublicKey() throws Exception {
        URL publicKeyURL = CredentialUtil.class.getClassLoader().getResource("keys/public-key");
        byte[] encodedPublicKey = Files.readAllBytes(Paths.get(publicKeyURL.toURI()));
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encodedPublicKey));
    }
}
