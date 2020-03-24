package com.example.oauth2.security.smsCode.register;

import com.example.oauth2.model.UserDeo;
import com.example.oauth2.security.smsCode.RedisCodeService;
import com.example.oauth2.security.smsCode.SmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SmsRegisterAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    private RedisCodeService redisCodeService;

    public SmsRegisterAuthenticationFilter() {
        super(new AntPathRequestMatcher("/registerMobile", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        if (postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + httpServletRequest.getMethod());
        }
        UserDeo userDeo = SmsUtils.obtainMobile(httpServletRequest);
        SmsUtils.validateSmsCode(redisCodeService, httpServletRequest, userDeo.getSmsCode(), userDeo.getMobile());

        SmsRegisterAuthenticationToken smsRegisterAuthenticationToken = new SmsRegisterAuthenticationToken(userDeo);

        setDetails(httpServletRequest, smsRegisterAuthenticationToken);

        return this.getAuthenticationManager().authenticate(smsRegisterAuthenticationToken);
    }

    protected void setDetails(HttpServletRequest request, SmsRegisterAuthenticationToken smsRegisterAuthenticationToken) {
        smsRegisterAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setRedisCodeService(RedisCodeService redisCodeService) {
        this.redisCodeService = redisCodeService;
    }
}
