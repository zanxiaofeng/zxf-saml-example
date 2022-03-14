package com.zxf.example.config;

import com.zxf.example.security.SAMLAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    SAMLAuthenticationFilter samlAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(samlAuthenticationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/saml/consume").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
