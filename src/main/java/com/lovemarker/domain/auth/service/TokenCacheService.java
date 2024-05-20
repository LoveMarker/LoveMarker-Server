package com.lovemarker.domain.auth.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenCacheService {
    private final RedisTemplate<String, String> redisTemplate;

    public void setValues(String key, String value, long expiryTime){
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expiryTime));
    }

    public String getValuesByKey(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValueByKey(String key){
        redisTemplate.delete(key);
    }
}
