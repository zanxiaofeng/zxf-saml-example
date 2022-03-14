package com.zxf.example.service;

import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.saml2.core.Response;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;


@Service
public class SAMLIssueService {
    public String issue(String userId, String orderId, String targetUrl, Integer expiredSeconds) throws Exception {
        Response response = SamlResponseBuilder.buildResponse(userId, orderId, targetUrl, expiredSeconds);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            XMLObjectSupport.marshallToOutputStream(response, outputStream);
            return new String(outputStream.toByteArray());
        }
    }
}
