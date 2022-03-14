package com.zxf.example.service;

import com.zxf.example.utils.CredentialUtil;
import com.zxf.example.utils.OpenSAMLUtil;
import org.joda.time.DateTime;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.*;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureConstants;
import org.opensaml.xmlsec.signature.support.Signer;

import java.io.InputStream;
import java.util.UUID;

public class SamlResponseBuilder {

    public static Response buildResponse(String userId, String orderId, String targetUrl, Integer expiredSeconds) throws Exception {
        DateTime now = DateTime.now();
        DateTime expired = DateTime.now().plusSeconds(expiredSeconds);

        Response templateResponse = loadTemplateResponse();
        templateResponse.setID(UUID.randomUUID().toString());
        templateResponse.setIssueInstant(now);
        templateResponse.setInResponseTo("");
        templateResponse.setDestination("");

        Assertion assertion = templateResponse.getAssertions().get(0);
        assertion.setID(UUID.randomUUID().toString());
        assertion.setIssueInstant(now);

        Subject subject = assertion.getSubject();
        subject.getSubjectConfirmations().get(0).getSubjectConfirmationData().setInResponseTo("");
        subject.getSubjectConfirmations().get(0).getSubjectConfirmationData().setNotOnOrAfter(expired);

        Conditions conditions = assertion.getConditions();
        conditions.setNotBefore(now);
        conditions.setNotOnOrAfter(expired);

        AuthnStatement authnStatement = assertion.getAuthnStatements().get(0);
        authnStatement.setAuthnInstant(now);

        AttributeStatement attributeStatement = assertion.getAttributeStatements().get(0);
        attributeStatement.getAttributes().get(0).getAttributeValues().get(0).getDOM().setTextContent(userId);
        attributeStatement.getAttributes().get(1).getAttributeValues().get(0).getDOM().setTextContent(orderId);
        attributeStatement.getAttributes().get(2).getAttributeValues().get(0).getDOM().setTextContent(targetUrl);

        signResponse(templateResponse);

        return templateResponse;
    }

    private static Response loadTemplateResponse() throws Exception {
        try (InputStream inputStream = SamlResponseBuilder.class.getClassLoader().getResourceAsStream("saml/response.xml")) {
            return (Response) XMLObjectSupport.unmarshallFromInputStream(XMLObjectProviderRegistrySupport.getParserPool(), inputStream);
        }
    }

    private static void signResponse(Response response) throws Exception {
        Signature signature = OpenSAMLUtil.buildSAMLObject(Signature.class);
        signature.setSigningCredential(CredentialUtil.getCredential());
        signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA256);
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
        response.setSignature(signature);
        XMLObjectSupport.marshall(response);
        Signer.signObject(signature);
    }
}
