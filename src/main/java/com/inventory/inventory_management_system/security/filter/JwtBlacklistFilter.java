package com.inventory.inventory_management_system.security.filter;

import com.inventory.inventory_management_system.security.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that checks whether the incoming Bearer token has been blacklisted
 * in Redis. If the token is found in the blacklist, the request is rejected
 * with a 401 Unauthorized response, effectively implementing server-side
 * token revocation.
 *
 * <p>This filter runs <em>before</em> {@link com.inventory.inventory_management_system.security.jwt.JwtAuthenticationFilter}
 * so that blacklisted tokens are rejected early in the security chain.</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtBlacklistFilter extends OncePerRequestFilter {

    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (tokenBlacklistService.isBlacklisted(token)) {
                log.warn("Rejected blacklisted token for request: {} {}", request.getMethod(), request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token has been revoked. Please log in again.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
