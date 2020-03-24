package com.example.oauth2.security.smsCode;


import com.example.oauth2.model.UserDeo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class SmsUtils {

    public static UserDeo obtainMobile(HttpServletRequest request) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(),
                    "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            ObjectMapper mapper = new ObjectMapper();
            UserDeo userDeo = mapper.readValue(responseStrBuilder.toString(), UserDeo.class);
            return userDeo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void validateSmsCode(RedisCodeService redisCodeService, HttpServletRequest httpServletRequest, String sms, String mobile) throws ServletRequestBindingException {
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
}




