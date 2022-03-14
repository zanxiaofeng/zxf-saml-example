package com.zxf.example.security;

import org.opensaml.saml.saml2.core.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SAMLAuthentication implements Authentication {
    private Response samlResponse;

    public SAMLAuthentication(Response samlResponse) {
        this.samlResponse = samlResponse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return samlResponse;
    }

    @Override
    public Object getPrincipal() {
        return samlResponse.getAssertions().get(0)
                .getAttributeStatements().get(0)
                .getAttributes().get(0)
                .getAttributeValues().get(0)
                .getDOM().getNodeValue();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public boolean equals(Object another) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}
