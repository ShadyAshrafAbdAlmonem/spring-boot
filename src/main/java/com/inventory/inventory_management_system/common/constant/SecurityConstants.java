package com.inventory.inventory_management_system.common.constant;

public final class SecurityConstants {
    private SecurityConstants() {}

    public static final String JWT_SECRET = "your-super-secret-key-that-is-long-and-secure"; // Replace with a strong secret, possibly from config
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/users/register";
}
