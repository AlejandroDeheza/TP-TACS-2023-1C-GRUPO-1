package com.tacs.backend.config;

import com.tacs.backend.repository.impl.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RateLimiterService {
    private static final Integer DEFAULT_API_RPM = 10;
    private static final Integer DEFAULT_USER_RPM = 10;
    private static final String GENERAL_KEY = "generalBackend";
    @Autowired
    private RedisRepository repository;

    public void incrementApiHitCount(String token) {
        repository.increment(token);
           repository.increment(GENERAL_KEY);
    }

    public boolean reachedMaxRequestAllowed(String token)  {
        Integer total = repository.getBy(GENERAL_KEY);
        if (total == null) {
            total = 0;
            repository.add(GENERAL_KEY, total);
        }
        return total > DEFAULT_API_RPM || reachedMaxUserRequestAllowed(token);
    }

    private boolean reachedMaxUserRequestAllowed(String token) {
        Integer count = repository.getBy(token);
        if ( count == null) {
            count = 0;
            repository.add(token, count);
        }
        return count > DEFAULT_USER_RPM;
    }
}