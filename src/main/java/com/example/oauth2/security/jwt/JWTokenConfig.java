package com.example.oauth2.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.concurrent.TimeUnit;

@Configuration
public class JWTokenConfig {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter()) {
            @Override
            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
                if (token.getAdditionalInformation().containsKey("jti")) {
                    String jti = token.getAdditionalInformation().get("jti").toString();
                    redisTemplate.opsForValue().set(jti, token.getValue(), token.getExpiresIn(), TimeUnit.SECONDS);
                }
                super.storeAccessToken(token, authentication);
            }

            public void removeAccessToken(OAuth2AccessToken token) {
                if (token.getAdditionalInformation().containsKey("jti")) {
                    String jti = token.getAdditionalInformation().get("jti").toString();
                    redisTemplate.delete(jti);
                }
                super.removeAccessToken(token);
            }
        };
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JWTokenEnhancer();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("test_key");
        accessTokenConverter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());
        return accessTokenConverter;
    }

    @Bean
    public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
        return new JWTRedisClaimsSetVerifier();
    }


}
