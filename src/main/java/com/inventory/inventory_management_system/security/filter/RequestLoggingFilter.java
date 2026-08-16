package com.inventory.inventory_management_system.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Security audit filter that logs every incoming HTTP request with key
 * security-relevant metadata (HTTP method, URI, client IP, user-agent,
 * authenticated principal if available, and timestamp).
 *
 * <p>This filter runs early in the security filter chain to provide a
 * complete audit trail of all requests hitting the application. All
 * requests – including those that eventually fail authentication – are
 * logged so that suspicious activity can be detected.</p>
 *
 * <p>The filter is intentionally lightweight: it delegates immediately to
 * the next filter in the chain and performs no request mutation, so it
 * has no functional impact on request processing.</p>
 */
@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        String method = request.getMethod();
        String requestUri = request.getRequestURI();
        String clientIp = extractClientIp(request);
        String userAgent = request.getHeader("User-Agent");
        String authHeader = request.getHeader("Authorization");

        // Log a masked version of the Authorization header for audit purposes
        String authInfo = "none";
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authInfo = "Bearer ***";
        }

        log.info("[SECURITY-AUDIT] {} {} from IP={} User-Agent=\"{}\" Auth={} at {}",
                method, requestUri, clientIp, userAgent, authInfo, LocalDateTime.now());

        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = System.currentTimeMillis() - startTime;
            log.info("[SECURITY-AUDIT] {} {} completed with status {} in {} ms",
                    method, requestUri, response.getStatus(), durationMs);
        }
    }

    /**
     * Extracts the real client IP, accounting for common reverse-proxy
     * headers (X-Forwarded-For, X-Real-IP).
     */
    private String extractClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }
        return request.getRemoteAddr();
    }
}
