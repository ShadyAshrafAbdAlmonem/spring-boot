package com.inventory.inventory_management_system.auth.service;

import com.inventory.inventory_management_system.auth.dto.request.LoginRequest;
import com.inventory.inventory_management_system.auth.dto.request.RefreshTokenRequest;
import com.inventory.inventory_management_system.auth.dto.request.RegisterRequest;
import com.inventory.inventory_management_system.auth.dto.response.JwtResponse;
import com.inventory.inventory_management_system.auth.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    JwtResponse register(RegisterRequest request);
    JwtResponse refreshToken(RefreshTokenRequest request);
    void logout(String token);
}