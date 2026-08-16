package com.inventory.inventory_management_system.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Service responsible for blacklisting JWT tokens in Redis.
 * <p>
 * When a user logs out, the token is added to the blacklist with a TTL
 * equal to the remaining validity period, ensuring that the blacklisted
 * token is automatically evicted once it would have naturally expired.
 */
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String BLACKLIST_PREFIX = "blacklist:token:";

    /**
     * Adds a token to the blacklist for the remaining duration of its validity.
     *
     * @param token      the JWT token to blacklist
     * @param ttlSeconds the time-to-live in seconds (remaining validity of the token)
     */
    public void blacklistToken(String token, long ttlSeconds) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "revoked", Duration.ofSeconds(ttlSeconds));
    }

    /**
     * Checks whether a token has been blacklisted.
     *
     * @param token the JWT token to check
     * @return true if the token is blacklisted, false otherwise
     */
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
