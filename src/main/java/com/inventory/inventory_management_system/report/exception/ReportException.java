package com.inventory.inventory_management_system.report.exception;

public class ReportException extends RuntimeException {
    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}