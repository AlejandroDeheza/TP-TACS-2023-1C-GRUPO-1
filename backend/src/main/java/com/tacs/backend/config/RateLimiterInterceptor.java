package com.tacs.backend.config;

import com.tacs.backend.exception.RequestNotAllowException;
import com.tacs.backend.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class RateLimiterInterceptor implements HandlerInterceptor {
    private final RateLimiterService rateLimiterService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        boolean reachedMaxRequestAllowed = rateLimiterService.reachedMaxRequestAllowed(token);
        if (reachedMaxRequestAllowed) {
            throw new RequestNotAllowException("User reached maximum number of request for application. Try again in a while.");
        }
        return true;
    }

}
