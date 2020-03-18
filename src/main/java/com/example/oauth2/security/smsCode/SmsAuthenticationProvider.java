package com.example.oauth2.security.smsCode;

import com.example.oauth2.security.model.UserDetailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        UserDetails userDetails = userDetailService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("未找到与该手机号对应的用户");
        }

        SmsAuthenticationToken smsAuthenticationResultToken = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        smsAuthenticationResultToken.setDetails(authenticationToken.getDetails());
        return smsAuthenticationResultToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }


}