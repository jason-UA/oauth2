package com.example.oauth2.security.smsCode;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private boolean postOnly = true;

    private RedisCodeService redisCodeService;

    public SmsAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/mobile", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + httpServletRequest.getMethod());
        }
        MobileSms mobileSms = SmsCodeUtils.obtainMobile(httpServletRequest);
        validateSmsCode(httpServletRequest, mobileSms.getSmsCode(), mobileSms.getMobile());

        String mobile = mobileSms.getMobile().trim();

        if (mobile == null) {
            mobile = "";
        }

        SmsAuthenticationToken smsAuthenticationToken = new SmsAuthenticationToken(mobile);

        setDetails(httpServletRequest, smsAuthenticationToken);

        return this.getAuthenticationManager().authenticate(smsAuthenticationToken);
    }

    protected void setDetails(HttpServletRequest request, SmsAuthenticationToken smsAuthenticationToken) {
        smsAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private void validateSmsCode(HttpServletRequest httpServletRequest, String sms, String mobile) throws ServletRequestBindingException {
        String codeInRedis = null;
        try {
            codeInRedis = redisCodeService.get(httpServletRequest, mobile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (StringUtils.isBlank(sms)) {
            throw new ValidateCodeException("验证码不能为空！");
        }
        if (codeInRedis == null) {
            throw new ValidateCodeException("验证码已过期，请重新发送！");
        }
        if (!StringUtils.equalsIgnoreCase(codeInRedis, sms)) {
            throw new ValidateCodeException("验证码不正确！");
        }
        try {
            redisCodeService.remove(httpServletRequest, mobile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public void setRedisCodeService(RedisCodeService redisCodeService) {
        this.redisCodeService = redisCodeService;
    }
}
