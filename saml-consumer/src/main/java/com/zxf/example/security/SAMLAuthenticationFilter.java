package com.zxf.example.security;

import com.zxf.example.service.SAMLTokenStore;
import org.opensaml.saml.saml2.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SAMLAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private SAMLTokenStore samlTokenStore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Response samlResponse = samlTokenStore.loadSAMLToken(request);
        if (samlResponse != null) {
            SecurityContextHolder.getContext().setAuthentication(new SAMLAuthentication(samlResponse));
        }

        filterChain.doFilter(request, response);
    }
}