package com.inventory.inventory_management_system.auth.service;

import com.inventory.inventory_management_system.auth.dto.request.RefreshTokenRequest;
import com.inventory.inventory_management_system.auth.dto.response.JwtResponse;
import com.inventory.inventory_management_system.auth.mapper.AuthMapper;
import com.inventory.inventory_management_system.auth.service.impl.AuthServiceImpl;
import com.inventory.inventory_management_system.auth.validator.PasswordValidator;
import com.inventory.inventory_management_system.common.enums.UserStatus;
import com.inventory.inventory_management_system.common.exception.UnauthorizedException;
import com.inventory.inventory_management_system.role.entity.Role;
import com.inventory.inventory_management_system.security.jwt.JwtService;
import com.inventory.inventory_management_system.security.service.TokenBlacklistService;
import com.inventory.inventory_management_system.user.entity.User;
import com.inventory.inventory_management_system.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTokenTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private PasswordValidator passwordValidator;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    private AuthServiceImpl authService;

    private User adminUser;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(
                userRepository, authMapper, passwordValidator, jwtService,
                authenticationManager, passwordEncoder, tokenBlacklistService
        );

        Role adminRole = Role.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();

        adminUser = User.builder()
                .id(1L)
                .username("admin")
                .email("admin@example.com")
                .password("$2a$10$encoded")
                .status(UserStatus.ACTIVE)
                .roles(Set.of(adminRole))
                .build();
    }

    @Test
    void refreshToken_shouldReturnNewAccessToken() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("valid-refresh-token");

        when(jwtService.extractUsername("valid-refresh-token")).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn("new-access-token");

        JwtResponse response = authService.refreshToken(request);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("new-access-token");
        assertThat(response.getRefreshToken()).isEqualTo("valid-refresh-token");
        assertThat(response.getUsername()).isEqualTo("admin");
        assertThat(response.getRoles()).containsExactly("ROLE_ADMIN");
    }

    @Test
    void refreshToken_shouldThrowUnauthorizedWhenUserNotFound() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("token");

        when(jwtService.extractUsername("token")).thenReturn("ghost");
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.refreshToken(request))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("ghost");
    }

    @Test
    void refreshToken_shouldThrowUnauthorizedWhenInvalidToken() {
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("expired-token");

        when(jwtService.extractUsername("expired-token")).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));
        when(jwtService.isTokenValid(anyString(), any())).thenReturn(false);

        assertThatThrownBy(() -> authService.refreshToken(request))
                .isInstanceOf(UnauthorizedException.class)
                .hasMessageContaining("Invalid or expired");
    }

    @Test
    void logout_shouldBlacklistToken() {
        when(jwtService.getRemainingSeconds("token-to-blacklist")).thenReturn(3600L);

        authService.logout("token-to-blacklist");

        verify(tokenBlacklistService).blacklistToken("token-to-blacklist", 3600L);
    }
}