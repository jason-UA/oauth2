package com.example.oauth2.security.smsCode.register;

import com.example.oauth2.model.UserDeo;
import com.example.oauth2.service.UserDetailService;
import com.example.oauth2.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class SmsRegisterAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsRegisterAuthenticationToken registerAuthenticationToken = (SmsRegisterAuthenticationToken) authentication;
        UserDeo userDeo = (UserDeo)registerAuthenticationToken.getPrincipal();
        UserDetails userDetails = userDetailService.insertUserByUser(userDeo);

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("未找到与该手机号对应的用户");
        }

        SmsRegisterAuthenticationToken smsAuthenticationResultToken = new SmsRegisterAuthenticationToken(userDetails, userDetails.getAuthorities());
        smsAuthenticationResultToken.setDetails(registerAuthenticationToken.getDetails());
        return smsAuthenticationResultToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsRegisterAuthenticationToken.class.isAssignableFrom(aClass);
    }

}
