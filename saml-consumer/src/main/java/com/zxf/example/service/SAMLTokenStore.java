package com.zxf.example.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.opensaml.saml.saml2.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.UUID;

@Service
public class SAMLTokenStore {
    @Value("${session.use}")
    Boolean sessionUse;
    @Autowired
    private Cache consumeCache;

    public void saveSAMLToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Response samlResponse) {
        if (sessionUse) {
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("samlResponse", samlResponse);
        } else {
            String mySessionId = genAndSaveMySessionId(httpResponse);
            consumeCache.put(new Element(mySessionId + "_samlResponse", samlResponse));
        }
    }

    public Response loadSAMLToken(HttpServletRequest httpRequest) {
        if (sessionUse) {
            HttpSession session = httpRequest.getSession(false);
            return (Response) session.getAttribute("samlResponse");
        } else {
            String mySessionId = getMySessionId(httpRequest);
            Element element = consumeCache.get(mySessionId + "_samlResponse");
            if (element == null) {
                return null;
            }
            return (Response) element.getObjectValue();
        }
    }

    public void removeSAMLToken(HttpServletRequest httpRequest) {
        if (sessionUse) {
            HttpSession session = httpRequest.getSession(false);
            session.invalidate();
        } else {
            String mySessionId = getMySessionId(httpRequest);
            consumeCache.remove(mySessionId + "_samlResponse");
        }
    }

    private String genAndSaveMySessionId(HttpServletResponse response) {
        String mySessionId = UUID.randomUUID().toString();
        response.addCookie(new Cookie("MY_SESSION_ID", mySessionId));
        return mySessionId;
    }

    private String getMySessionId(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("MY_SESSION_ID"))
                .findFirst()
                .map(Cookie::getValue).orElse("");
    }
}
