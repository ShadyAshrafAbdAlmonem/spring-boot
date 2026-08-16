package com.inventory.inventory_management_system.upload.exception;

import com.inventory.inventory_management_system.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidFileException extends BusinessException {
    public InvalidFileException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
