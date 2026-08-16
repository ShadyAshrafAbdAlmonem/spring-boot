package com.inventory.inventory_management_system.common.util;

import java.util.regex.Pattern;

/**
 * Utility class for common validations.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[1-9]\\d{1,14}$"
    );

    private static final Pattern USERNAME_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_-]{3,20}$"
    );

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$"
    );

    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    );

    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]+$");
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^[0-9]+\\.[0-9]+$");
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    // ==================== String Validations ====================

    /**
     * Check if string is null or empty
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if string is not null and not empty
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        return !isBlank(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate phone number format (E.164 format)
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !isBlank(phoneNumber) && PHONE_PATTERN.matcher(phoneNumber.replaceAll("\\s", "")).matches();
    }

    /**
     * Validate username format (alphanumeric, underscore, dash, 3-20 chars)
     */
    public static boolean isValidUsername(String username) {
        return !isBlank(username) && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Validate URL format
     */
    public static boolean isValidUrl(String url) {
        return !isBlank(url) && URL_PATTERN.matcher(url).matches();
    }

    /**
     * Validate UUID format
     */
    public static boolean isValidUUID(String uuid) {
        return !isBlank(uuid) && UUID_PATTERN.matcher(uuid).matches();
    }

    /**
     * Validate IP address format
     */
    public static boolean isValidIPAddress(String ipAddress) {
        return !isBlank(ipAddress) && IP_ADDRESS_PATTERN.matcher(ipAddress).matches();
    }

    // ==================== Numeric Validations ====================

    /**
     * Check if string is numeric
     */
    public static boolean isNumeric(String str) {
        return !isBlank(str) && NUMERIC_PATTERN.matcher(str).matches();
    }

    /**
     * Check if string is decimal
     */
    public static boolean isDecimal(String str) {
        return !isBlank(str) && DECIMAL_PATTERN.matcher(str).matches();
    }

    /**
     * Check if string is alphanumeric
     */
    public static boolean isAlphanumeric(String str) {
        return !isBlank(str) && ALPHANUMERIC_PATTERN.matcher(str).matches();
    }

    /**
     * Validate range (inclusive)
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Validate range (inclusive)
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Validate positive number
     */
    public static boolean isPositive(int value) {
        return value > 0;
    }

    /**
     * Validate positive number
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }

    /**
     * Validate non-negative number
     */
    public static boolean isNonNegative(int value) {
        return value >= 0;
    }

    /**
     * Validate non-negative number
     */
    public static boolean isNonNegative(double value) {
        return value >= 0;
    }

    // ==================== Length Validations ====================

    /**
     * Validate string length range
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (isBlank(str)) {
            return minLength == 0;
        }
        int length = str.length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Validate minimum length
     */
    public static boolean hasMinLength(String str, int minLength) {
        return !isBlank(str) && str.length() >= minLength;
    }

    /**
     * Validate maximum length
     */
    public static boolean hasMaxLength(String str, int maxLength) {
        return isBlank(str) || str.length() <= maxLength;
    }

    // ==================== Password Validations ====================

    /**
     * Validate password strength (minimum 8 characters with mixed case, digits, and special chars)
     */
    public static boolean isValidPasswordStrength(String password) {
        if (isBlank(password) || password.length() < 8) {
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
            } else if (!Character.isWhitespace(c)) {
                hasSpecialChar = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    /**
     * Check if two passwords match
     */
    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    // ==================== Text Validations ====================

    /**
     * Check if text contains only letters
     */
    public static boolean isAlphabetic(String str) {
        return !isBlank(str) && str.matches("[a-zA-Z]+");
    }

    /**
     * Check if text contains only letters and spaces
     */
    public static boolean isAlphabeticWithSpaces(String str) {
        return !isBlank(str) && str.matches("[a-zA-Z\\s]+");
    }

    /**
     * Validate credit card number format (basic Luhn check not included)
     */
    public static boolean isValidCreditCardNumber(String cardNumber) {
        if (isBlank(cardNumber)) {
            return false;
        }
        String digitsOnly = cardNumber.replaceAll("\\s", "").replaceAll("-", "");
        return digitsOnly.matches("\\d{13,19}");
    }

    /**
     * Validate IBAN format (basic format check)
     */
    public static boolean isValidIBAN(String iban) {
        if (isBlank(iban)) {
            return false;
        }
        String cleanIban = iban.replaceAll("\\s", "").toUpperCase();
        return cleanIban.matches("[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}");
    }

    // ==================== Collection Validations ====================

    /**
     * Validate collection is not null and not empty
     */
    public static boolean isValidCollection(Iterable<?> collection) {
        if (collection == null) {
            return false;
        }
        if (collection instanceof java.util.Collection<?> col) {
            return !col.isEmpty();
        }
        return collection.iterator().hasNext();
    }

    /**
     * Validate array is not null and not empty
     */
    public static boolean isValidArray(Object[] array) {
        return array != null && array.length > 0;
    }

    // ==================== Utility Methods ====================

    /**
     * Sanitize string by removing potentially dangerous characters
     */
    public static String sanitize(String input) {
        if (isBlank(input)) {
            return input;
        }
        return input.replaceAll("[<>\"']", "");
    }

    /**
     * Validate and return sanitized string or throw exception
     */
    public static String requireNonBlank(String input, String fieldName) {
        if (isBlank(input)) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        return sanitize(input);
    }

    /**
     * Check if string contains only ASCII characters
     */
    public static boolean isASCII(String str) {
        if (isBlank(str)) {
            return true;
        }
        return str.chars().allMatch(c -> c < 128);
    }

    /**
     * Validate hex color code format
     */
    public static boolean isValidHexColor(String color) {
        return !isBlank(color) && color.matches("^#?([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }
}
