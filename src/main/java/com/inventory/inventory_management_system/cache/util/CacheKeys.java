package com.inventory.inventory_management_system.cache.util;

public class CacheKeys {

    public static final String CATEGORIES_KEY = "categories";
    public static final String BRANDS_KEY = "brands";

    public static String getCategoryKey(Long id) {
        return CATEGORIES_KEY + "::" + id;
    }
}
