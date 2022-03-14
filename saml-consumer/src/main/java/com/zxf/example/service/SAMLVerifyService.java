package com.zxf.example.service;

import com.zxf.example.utils.CredentialUtil;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.security.impl.SAMLSignatureProfileValidator;
import org.opensaml.xmlsec.signature.support.SignatureValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Service
public class SAMLVerifyService {
    public Response verify(String saml) throws Exception {
        Response samlResponse = (Response) XMLObjectSupport.unmarshallFromReader(XMLObjectProviderRegistrySupport.getParserPool(), new StringReader(saml));
        if (!samlResponse.isSigned()) {
            throw new RuntimeException("The SAML Assertion was not signed");
        }

        SAMLSignatureProfileValidator profileValidator = new SAMLSignatureProfileValidator();
        profileValidator.validate(samlResponse.getSignature());

        SignatureValidator.validate(samlResponse.getSignature(), CredentialUtil.getCredential());

        return samlResponse;
    }
}
