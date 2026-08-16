package com.inventory.inventory_management_system.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class for password operations.
 */
public class PasswordUtil {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final int DEFAULT_PASSWORD_LENGTH = 12;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Random RANDOM = new SecureRandom();

    /**
     * Encode raw password using BCrypt
     */
    public static String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Match raw password with encoded password
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Check if password needs re-encoding (e.g., after changing encoder strength)
     */
    public static boolean needsReencoding(String encodedPassword) {
        return passwordEncoder.upgradeEncoding(encodedPassword);
    }

    /**
     * Generate random password with default length
     */
    public static String generateRandomPassword() {
        return generateRandomPassword(DEFAULT_PASSWORD_LENGTH);
    }

    /**
     * Generate random password with specified length
     */
    public static String generateRandomPassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 4");
        }

        StringBuilder password = new StringBuilder(length);
        String charSet = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;

        // Ensure at least one character from each category
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARS.charAt(RANDOM.nextInt(SPECIAL_CHARS.length())));

        // Fill the rest with random characters
        for (int i = 4; i < length; i++) {
            password.append(charSet.charAt(RANDOM.nextInt(charSet.length())));
        }

        // Shuffle the password characters
        return shuffleString(password.toString());
    }

    /**
     * Generate random alphanumeric password (no special characters)
     */
    public static String generateAlphanumericPassword(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be at least 1");
        }

        String charSet = UPPERCASE + LOWERCASE + DIGITS;
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(charSet.charAt(RANDOM.nextInt(charSet.length())));
        }

        return password.toString();
    }

    /**
     * Validate password strength
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (SPECIAL_CHARS.indexOf(c) != -1) {
                hasSpecialChar = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    /**
     * Calculate password strength score (0-5)
     */
    public static int calculatePasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }

        int score = 0;

        // Length checks
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;
        if (password.length() >= 16) score++;

        // Character variety checks
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*")) score++;

        // Normalize to 0-5
        return Math.min(5, score);
    }

    /**
     * Get password strength description
     */
    public static String getPasswordStrengthDescription(String password) {
        int strength = calculatePasswordStrength(password);
        return switch (strength) {
            case 0, 1 -> "Very Weak";
            case 2 -> "Weak";
            case 3 -> "Fair";
            case 4 -> "Strong";
            case 5 -> "Very Strong";
            default -> "Unknown";
        };
    }

    /**
     * Shuffle characters in a string (Fisher-Yates algorithm)
     */
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }

    /**
     * Check if password contains only digits
     */
    public static boolean isNumeric(String password) {
        return password != null && password.matches("\\d+");
    }

    /**
     * Check if password contains only letters
     */
    public static boolean isAlphabetic(String password) {
        return password != null && password.matches("[a-zA-Z]+");
    }

    /**
     * Check if password is alphanumeric
     */
    public static boolean isAlphanumeric(String password) {
        return password != null && password.matches("[a-zA-Z0-9]+");
    }

    /**
     * Mask password for logging (e.g., "abc123" -> "***")
     */
    public static String maskPassword(String password) {
        return password != null ? "***" : null;
    }

    /**
     * Mask email address for logging (e.g., "user@example.com" -> "u***@example.com")
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@");
        String masked = parts[0].substring(0, 1) + "***";
        return masked + "@" + parts[1];
    }
}
