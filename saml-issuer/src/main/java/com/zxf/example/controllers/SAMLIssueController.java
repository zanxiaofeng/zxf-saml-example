package com.zxf.example.controllers;

import com.zxf.example.service.SAMLIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class SAMLIssueController {
    @Autowired
    private SAMLIssueService samlIssueService;

    @GetMapping("/saml/issue")
    public ModelAndView issue(@RequestParam String userId, @RequestParam String orderId, @RequestParam String targetUrl) throws Exception {
        System.out.println("SAMLIssueController::issue: " + userId + ", " + orderId + ", " + targetUrl);
        String saml = samlIssueService.issue(userId, orderId, targetUrl, 30);
        ModelAndView modelAndView = new ModelAndView("saml_submit");
        modelAndView.addObject("saml", saml);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("orderId", orderId);
        modelAndView.addObject("targetUrl", targetUrl);
        return modelAndView;
    }
}
