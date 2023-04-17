package com.tacs.backend.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
public class RateLimiterServiceTest {

    private static final Integer DEFAULT_API_RPM = 3;
    private static final Integer DEFAULT_USER_RPM = 2;

    @Test
    public void testReachedMaxRequestAllowed() {
        RateLimiterService service = new RateLimiterService();
        String token = "token123";
        service.initializeUserRequest(token);
        assertFalse(service.reachedMaxRequestAllowed(token), "First request should be allowed");
        assertFalse(service.reachedMaxRequestAllowed(token), "Second request should be allowed");
        assertTrue(service.reachedMaxRequestAllowed(token), "Third request is not allowed.");

    }
}
