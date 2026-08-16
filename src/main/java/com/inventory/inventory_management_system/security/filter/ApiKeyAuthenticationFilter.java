package com.inventory.inventory_management_system.security.filter;

import com.inventory.inventory_management_system.common.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Simple API key authentication filter example.
 * This filter validates an API key from the request header.
 */
@Component
@Slf4j
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String VALID_API_KEY = "your-secret-api-key";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // Skip filter for public endpoints
        if (isPublicEndpoint(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);

        if (apiKey == null || !VALID_API_KEY.equals(apiKey)) {
            log.warn("Invalid or missing API key for request: {}", requestUri);
            throw new UnauthorizedException("Invalid or missing API key");
        }

        log.debug("API key validated successfully for request: {}", requestUri);
        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String requestUri) {
        // Define public endpoints that don't require authentication
        return requestUri.contains("/api/v1/auth/") ||
               requestUri.contains("/actuator/") ||
               requestUri.contains("/swagger-ui") ||
               requestUri.contains("/v3/api-docs");
    }
}
