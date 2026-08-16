package com.inventory.inventory_management_system.cache.service;

import org.springframework.lang.NonNull;

import java.time.Duration;

public interface CacheService {
    void put(@NonNull String key, @NonNull Object value); // Already has @NonNull
    void put(@NonNull String key, @NonNull Object value, @NonNull Duration ttl); // Already has @NonNull
    Object get(@NonNull String key); // Already has @NonNull
    void delete(@NonNull String key); // Already has @NonNull
    boolean hasKey(@NonNull String key); // Already has @NonNull
}
