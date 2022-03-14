package com.zxf.example.config;

import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class OpenSamlConfig {
    @PostConstruct
    public void configuration() throws InitializationException {
        InitializationService.initialize();
    }
}
