package com.inventory.inventory_management_system.auth.service;

import com.inventory.inventory_management_system.auth.dto.request.LoginRequest;
import com.inventory.inventory_management_system.auth.dto.request.RefreshTokenRequest;
import com.inventory.inventory_management_system.auth.dto.request.RegisterRequest;
import com.inventory.inventory_management_system.auth.dto.response.JwtResponse;
import com.inventory.inventory_management_system.auth.dto.response.LoginResponse;
import com.inventory.inventory_management_system.auth.mapper.AuthMapper;
import com.inventory.inventory_management_system.auth.service.impl.AuthServiceImpl;
import com.inventory.inventory_management_system.auth.validator.PasswordValidator;
import com.inventory.inventory_management_system.common.enums.UserStatus;
import com.inventory.inventory_management_system.common.exception.BadRequestException;
import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.common.exception.UnauthorizedException;
import com.inventory.inventory_management_system.role.entity.Role;
import com.inventory.inventory_management_system.security.jwt.JwtService;
import com.inventory.inventory_management_system.security.service.TokenBlacklistService;
import com.inventory.inventory_management_system.security.userdetails.CustomUserDetails;
import com.inventory.inventory_management_system.user.entity.User;
import com.inventory.inventory_management_system.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

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
    private CustomUserDetails adminDetails;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(
                userRepository, authMapper, passwordValidator, jwtService,
                authenticationManager, passwordEncoder, tokenBlacklistService
        );

        adminRole = Role.builder()
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

        adminDetails = CustomUserDetails.builder()
                .id(1L)
                .username("admin")
                .password("$2a$10$encoded")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .enabled(true)
                .build();
    }

    @Test
    void login_shouldReturnTokensAndUserInfo() {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("admin");
        request.setPassword("Admin123!");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(adminDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(adminDetails))
                .thenReturn("access-token")
                .thenReturn("refresh-token");

        LoginResponse response = authService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.getJwt()).isNotNull();
        assertThat(response.getJwt().getAccessToken()).isEqualTo("access-token");
        assertThat(response.getJwt().getRefreshToken()).isEqualTo("refresh-token");
        assertThat(response.getJwt().getUsername()).isEqualTo("admin");
        assertThat(response.getJwt().getRoles()).containsExactly("ROLE_ADMIN");
    }
}