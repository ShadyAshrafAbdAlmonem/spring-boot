package com.inventory.inventory_management_system.common.constant;

public final class ApiPaths {
    private ApiPaths() {} // Prevent instantiation

    public static final String BASE_PATH = "/api/v1";
    public static final String PRODUCTS = BASE_PATH + "/products";
    public static final String ORDERS = BASE_PATH + "/orders";
    public static final String STOCK_MOVEMENTS = BASE_PATH + "/stock-movements";
}