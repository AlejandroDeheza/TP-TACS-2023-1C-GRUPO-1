package com.tacs.backend.config;

import com.tacs.backend.exception.RequestNotAllowException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class RateLimiterInterceptor extends OncePerRequestFilter {
    @Autowired
    private final RateLimiterService rateLimiterService;
    public void preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isBlank(authorization)){
            return;
        }
        String token = authorization.split(" ")[1];
        boolean reachedMaxRequestAllowed = rateLimiterService.reachedMaxRequestAllowed(token);
        if (reachedMaxRequestAllowed) {
            throw new RequestNotAllowException("User reached maximum number of request for application. Try again in a while.");
        }
        rateLimiterService.incrementApiHitCount(token);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {{
        try {
            logger.info("--------Entering filter------");
            preHandle(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }
}
}
