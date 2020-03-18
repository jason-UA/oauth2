package com.example.oauth2.security.controller;

import com.example.oauth2.security.smsCode.SmsCode;
import com.example.oauth2.security.smsCode.RedisCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Slf4j
public class ValidateController {

    public final static String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";

    @Autowired
    RedisCodeService redisCodeService;

    @GetMapping("/code/sms")
    public String createSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile) throws IOException {
        SmsCode smsCode = createSMSCode();
        try {
            redisCodeService.save(smsCode, new ServletWebRequest(request), mobile);
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
}
