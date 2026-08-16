package com.inventory.inventory_management_system.upload.exception;

import com.inventory.inventory_management_system.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class FileTooLargeException extends BusinessException {
    public FileTooLargeException(String message) {
        super(message, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
