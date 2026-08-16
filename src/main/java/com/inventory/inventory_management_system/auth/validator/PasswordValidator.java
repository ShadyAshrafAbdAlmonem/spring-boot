package com.inventory.inventory_management_system.auth.validator;

import com.inventory.inventory_management_system.common.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    public void validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new BadRequestException("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new BadRequestException("Password must contain at least one digit");
        }
    }
}
