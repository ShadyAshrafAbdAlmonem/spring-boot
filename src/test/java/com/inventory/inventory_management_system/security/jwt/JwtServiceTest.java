package com.inventory.inventory_management_system.security.jwt;

import com.inventory.inventory_management_system.security.userdetails.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private JwtService jwtService;

    // Base64-encoded 256-bit key for testing
    private static final String TEST_SECRET =
            "ZGV2LXRlc3Qtc2VjcmV0LWtleS1mb3ItdGVzdGluZy1vbmx5LXRlc3Qtc2VjcmV0LWtleQ==";

    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", TEST_SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L); // 24h

        userDetails = CustomUserDetails.builder()
                .id(1L)
                .username("admin")
                .password("encoded-password")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .enabled(true)
                .build();
    }

    @Test
    void generateToken_shouldReturnNonBlankToken() {
        String token = jwtService.generateToken(userDetails);

        assertThat(token).isNotBlank();
        assertThat(jwtService.extractUsername(token)).isEqualTo("admin");
    }

    @Test
    void extractUsername_shouldReturnSubjectFromToken() {
        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertThat(username).isEqualTo("admin");
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        String token = jwtService.generateToken(userDetails);

        boolean valid = jwtService.isTokenValid(token, userDetails);

        assertThat(valid).isTrue();
    }

    @Test
    void isTokenValid_shouldReturnFalseForDifferentUser() {
        String token = jwtService.generateToken(userDetails);

        CustomUserDetails otherUser = CustomUserDetails.builder()
                .id(2L)
                .username("manager")
                .password("encoded-password")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_MANAGER")))
                .enabled(true)
                .build();

        boolean valid = jwtService.isTokenValid(token, otherUser);

        assertThat(valid).isFalse();
    }

    @Test
    void getRemainingSeconds_shouldReturnPositiveValueForFreshToken() {
        String token = jwtService.generateToken(userDetails);

        long remaining = jwtService.getRemainingSeconds(token);

        assertThat(remaining).isPositive();
        assertThat(remaining).isLessThanOrEqualTo(86400L);
    }

    @Test
    void validateSecretKey_shouldThrowWhenSecretIsBlank() {
        JwtService blankSecretService = new JwtService();
        ReflectionTestUtils.setField(blankSecretService, "secretKey", "");
        ReflectionTestUtils.setField(blankSecretService, "jwtExpiration", 86400000L);

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                blankSecretService::validateSecretKey
        );
    }
}