package com.inventory.inventory_management_system.upload.exception;

import com.inventory.inventory_management_system.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class FileUploadException extends BusinessException {
    public FileUploadException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message + (cause != null ? ": " + cause.getMessage() : ""), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
