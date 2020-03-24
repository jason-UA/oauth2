package com.example.oauth2.security.smsCode.register;

import com.example.oauth2.service.UserDetailService;
import com.example.oauth2.security.smsCode.RedisCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsRegisterAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private RedisCodeService redisCodeService;


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SmsRegisterAuthenticationFilter smsRegisterAuthenticationFilter = new SmsRegisterAuthenticationFilter();
        smsRegisterAuthenticationFilter.setRedisCodeService(redisCodeService);
        smsRegisterAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        smsRegisterAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        smsRegisterAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        SmsRegisterAuthenticationProvider smsregisterAuthenticationProvider = new SmsRegisterAuthenticationProvider();
        smsregisterAuthenticationProvider.setUserDetailService(userDetailService);

        builder.authenticationProvider(smsregisterAuthenticationProvider)
                .addFilterBefore(smsRegisterAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
