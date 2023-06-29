package com.tacs.backend.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    @Autowired
    @Qualifier(value = "rate-limiter")
    public RedisTemplate<String, Integer> template;

    public Integer getBy(String key) {
        return template.opsForValue().get(key);
    }
    public void add(String key, Integer value) {
        template.opsForValue().set(key, value);
        template.expire(key, java.time.Duration.ofMinutes(1));
    }
    public void increment(String key) {
        template.opsForValue().increment(key);
        template.expire(key, java.time.Duration.ofMinutes(1));
    }
}
