package com.inventory.inventory_management_system.auth.service.impl;

import com.inventory.inventory_management_system.auth.dto.request.LoginRequest;
import com.inventory.inventory_management_system.auth.dto.request.RefreshTokenRequest;
import com.inventory.inventory_management_system.auth.dto.request.RegisterRequest;
import com.inventory.inventory_management_system.auth.dto.response.JwtResponse;
import com.inventory.inventory_management_system.auth.dto.response.LoginResponse;
import com.inventory.inventory_management_system.auth.mapper.AuthMapper;
import com.inventory.inventory_management_system.auth.service.AuthService;
import com.inventory.inventory_management_system.auth.validator.PasswordValidator;
import com.inventory.inventory_management_system.common.enums.UserStatus;
import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.common.exception.UnauthorizedException;
import com.inventory.inventory_management_system.security.jwt.JwtService;
import com.inventory.inventory_management_system.security.service.TokenBlacklistService;
import com.inventory.inventory_management_system.security.userdetails.CustomUserDetails;
import com.inventory.inventory_management_system.user.entity.User;
import com.inventory.inventory_management_system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final PasswordValidator passwordValidator;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateToken(userDetails);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());

        JwtResponse jwt = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

        return LoginResponse.builder()
                .jwt(jwt)
                .message("Login successful")
                .build();
    }

    @Override
    @Transactional
    public JwtResponse register(RegisterRequest request) {
        passwordValidator.validatePasswordStrength(request.getPassword());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already taken");
        }

        User user = authMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .enabled(true)
                .build();

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateToken(userDetails);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("User not found: " + username));

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());

        CustomUserDetails userDetails = CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .enabled(true)
                .build();

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new UnauthorizedException("Invalid or expired refresh token");
        }

        String newAccessToken = jwtService.generateToken(userDetails);

        return JwtResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

    @Override
    public void logout(String token) {
        long remainingSeconds = jwtService.getRemainingSeconds(token);
        tokenBlacklistService.blacklistToken(token, remainingSeconds);
        log.info("Token successfully blacklisted for {} seconds", remainingSeconds);
    }
}
