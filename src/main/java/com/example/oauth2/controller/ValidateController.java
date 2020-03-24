package com.example.oauth2.controller;

import com.example.oauth2.security.smsCode.RedisCodeService;
import com.example.oauth2.security.smsCode.SmsCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class ValidateController {

    public final static String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";

    @Autowired
    RedisCodeService redisCodeService;

    @Autowired
    TokenStore tokenStore;

    @GetMapping("/codeSms")
    public String createSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile) throws IOException {
        SmsCode smsCode = createSMSCode();
        try {
            redisCodeService.save(smsCode, request, mobile);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        // 输出验证码到控制台代替短信发送服务
        log.info("您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
        return smsCode.getCode();

    }

    private SmsCode createSMSCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new SmsCode(code, 60);
    }


    @GetMapping("/revokeToken")
    public String logout(@AuthenticationPrincipal Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer ");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return "注销成功";
    }
}
