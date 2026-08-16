package com.inventory.inventory_management_system.upload.exception;

import com.inventory.inventory_management_system.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UnsupportedFileTypeException extends BusinessException {
    public UnsupportedFileTypeException(String message) {
        super(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
}
