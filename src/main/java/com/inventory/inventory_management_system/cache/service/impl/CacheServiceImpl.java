package com.inventory.inventory_management_system.cache.service.impl;

import com.inventory.inventory_management_system.cache.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void put(@NonNull String key, @NonNull Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void put(@NonNull String key, @NonNull Object value, @NonNull Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    @Override
    public Object get(@NonNull String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(@NonNull String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(@NonNull String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}