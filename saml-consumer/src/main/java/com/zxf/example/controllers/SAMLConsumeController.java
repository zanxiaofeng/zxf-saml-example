package com.zxf.example.controllers;

import com.zxf.example.service.SAMLTokenStore;
import com.zxf.example.service.SAMLVerifyService;
import org.opensaml.saml.saml2.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SAMLConsumeController {
    @Autowired
    private SAMLVerifyService samlVerifyService;
    @Autowired
    private SAMLTokenStore samlTokenStore;

    @PostMapping("/saml/consume")
    public ModelAndView consume(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @ModelAttribute ConsumeRequest request) throws Exception {
        System.out.println("SAMLConsumeController::consume: " + request.getSaml());
        Response samlResponse = samlVerifyService.verify(request.getSaml());
        samlTokenStore.saveSAMLToken(httpRequest, httpResponse, samlResponse);
        ModelAndView modelAndView = new ModelAndView("consume");
        modelAndView.addObject("result", samlResponse);
        return modelAndView;
    }

    @GetMapping("/saml/consume/result")
    public ModelAndView consumeResult(HttpServletRequest httpRequest) {
        System.out.println("SAMLConsumeController::consume.result");
        Response samlResponse = samlTokenStore.loadSAMLToken(httpRequest);
        ModelAndView modelAndView = new ModelAndView("consume_result");
        modelAndView.addObject("result", samlResponse);
        return modelAndView;
    }

    @GetMapping("/saml/consume/close")
    public ModelAndView consumeClose(HttpServletRequest httpRequest) {
        System.out.println("SAMLConsumeController::consume.close");
        samlTokenStore.removeSAMLToken(httpRequest);
        SecurityContextHolder.getContext().setAuthentication(null);
        ModelAndView modelAndView = new ModelAndView("consume_close");
        return modelAndView;
    }

    public static class ConsumeRequest {
        private String saml;

        public String getSaml() {
            return saml;
        }

        public void setSaml(String saml) {
            this.saml = saml;
        }
    }
}
