package com.inventory.inventory_management_system.auth.service;

import com.inventory.inventory_management_system.auth.dto.request.RegisterRequest;
import com.inventory.inventory_management_system.auth.dto.response.JwtResponse;
import com.inventory.inventory_management_system.auth.mapper.AuthMapper;
import com.inventory.inventory_management_system.auth.service.impl.AuthServiceImpl;
import com.inventory.inventory_management_system.auth.validator.PasswordValidator;
import com.inventory.inventory_management_system.common.enums.UserStatus;
import com.inventory.inventory_management_system.common.exception.BadRequestException;
import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplRegistrationTest {

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
    void register_shouldCreateUserAndReturnJwt() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setEmail("newuser@example.com");
        request.setPassword("StrongPass1");

        User newUser = User.builder()
                .username("newuser")
                .email("newuser@example.com")
                .build();

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(authMapper.toUserEntity(request)).thenReturn(newUser);
        when(passwordEncoder.encode("StrongPass1")).thenReturn("$2a$10$encoded-new");
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(jwtService.generateToken(any(CustomUserDetails.class)))
                .thenReturn("new-access-token")
                .thenReturn("new-refresh-token");

        JwtResponse response = authService.register(request);

        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("new-access-token");
        assertThat(response.getRefreshToken()).isEqualTo("new-refresh-token");
        assertThat(response.getUsername()).isEqualTo("newuser");
        assertThat(newUser.getPassword()).isEqualTo("$2a$10$encoded-new");
        assertThat(newUser.getStatus()).isEqualTo(UserStatus.ACTIVE);

        verify(passwordValidator).validatePasswordStrength("StrongPass1");
        verify(userRepository).save(newUser);
    }

    @Test
    void register_shouldThrowDuplicateUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("admin");
        request.setEmail("newuser@example.com");
        request.setPassword("StrongPass1");

        when(userRepository.existsByUsername("admin")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Username");

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_shouldThrowDuplicateEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setEmail("admin@example.com");
        request.setPassword("StrongPass1");

        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("admin@example.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Email");

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_shouldThrowWeakPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("newuser");
        request.setEmail("newuser@example.com");
        request.setPassword("weak");

        doThrow(new BadRequestException("Password must be at least 8 characters long"))
                .when(passwordValidator).validatePasswordStrength("weak");

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(BadRequestException.class);

        verify(userRepository, never()).save(any());
    }
}