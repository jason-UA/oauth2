package com.example.oauth2.security.config;

import com.example.oauth2.security.smsCode.login.SmsAuthenticationConfig;
import com.example.oauth2.security.handler.MyAuthenticationFailureHandler;
import com.example.oauth2.security.handler.MyAuthenticationSuccessHandler;
import com.example.oauth2.security.smsCode.register.SmsRegisterAuthenticationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;

    @Autowired
    private SmsRegisterAuthenticationConfig smsRegisterAuthenticationConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/codeSms", "/revokeToken")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .apply(smsAuthenticationConfig)
                .and()
                .apply(smsRegisterAuthenticationConfig);
    }
}
