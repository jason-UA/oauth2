package com.example.oauth2.security.smsCode;

import com.example.oauth2.security.smsCode.SmsCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCodeService {

    private final static String SMS_CODE_PREFIX = "SMS_CODE:";
    private final static Integer TIME_OUT = 60;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 保存验证码到 redis
     *
     * @param smsCode 短信验证码
     * @param request ServletWebRequest
     */
    public void save(SmsCode smsCode, HttpServletRequest request, String mobile) throws Exception {
        redisTemplate.opsForValue().set(key(request, mobile), smsCode.getCode(), TIME_OUT, TimeUnit.SECONDS);
    }

    /**
     * 获取验证码
     *
     * @param request ServletWebRequest
     * @return 验证码
     */
    public String get(HttpServletRequest request, String mobile) throws Exception {
        return redisTemplate.opsForValue().get(key(request, mobile));
    }

    /**
     * 移除验证码
     *
     * @param request ServletWebRequest
     */
    public void remove(HttpServletRequest request, String mobile) throws Exception {
        redisTemplate.delete(key(request, mobile));
    }

    private String key(HttpServletRequest request, String mobile) throws Exception {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new Exception("请在请求头中设置deviceId");
        }
        return SMS_CODE_PREFIX + deviceId + ":" + mobile;
    }
}
