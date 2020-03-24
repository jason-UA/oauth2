package com.example.oauth2.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;

import java.util.Map;

public class JWTRedisClaimsSetVerifier implements JwtClaimsSetVerifier {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void verify(Map<String, Object> map) throws InvalidTokenException {
        if (map.containsKey("jti")) {
            String jti = map.get("jti").toString();
            Object value = redisTemplate.opsForValue().get(jti);
            if (value == null) {
                throw new InvalidTokenException("无效的令牌");
            }
        }
    }
}
