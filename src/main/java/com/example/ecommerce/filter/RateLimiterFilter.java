package com.example.ecommerce.filter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class RateLimiterFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);
    private final RateLimiter rateLimiter;

    public RateLimiterFilter(RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiter = rateLimiterRegistry.rateLimiter("apiLimiter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.getRequestURI().startsWith("/api")) {
            if (!rateLimiter.acquirePermission()) {
                logger.warn("[RateLimiter] TOO MANY REQUESTS: {}", httpRequest.getRequestURI());
                httpResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests. Try again later.");

                return;
            }
        }

        chain.doFilter(request, response);
    }
}
