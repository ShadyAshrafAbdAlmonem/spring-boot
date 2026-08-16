package com.inventory.inventory_management_system.common.enums;

/**
 * Payment status enumeration for tracking payment states.
 */
public enum PaymentStatus {
    
    PENDING("Pending", "Payment is pending"),
    PROCESSING("Processing", "Payment is being processed"),
    COMPLETED("Completed", "Payment completed successfully"),
    FAILED("Failed", "Payment failed"),
    CANCELLED("Cancelled", "Payment was cancelled"),
    REFUNDED("Refunded", "Payment was refunded"),
    PARTIALLY_REFUNDED("Partially Refunded", "Payment was partially refunded"),
    ON_HOLD("On Hold", "Payment is on hold"),
    VOID("Void", "Payment was voided");

    private final String displayName;
    private final String description;

    PaymentStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Check if the status indicates a successful payment
     */
    public boolean isSuccessful() {
        return this == COMPLETED;
    }

    /**
     * Check if the status indicates a failed payment
     */
    public boolean isFailed() {
        return this == FAILED || this == CANCELLED || this == VOID;
    }

    /**
     * Check if the status indicates payment is still pending
     */
    public boolean isPending() {
        return this == PENDING || this == PROCESSING || this == ON_HOLD;
    }

    /**
     * Check if the status indicates a refund
     */
    public boolean isRefunded() {
        return this == REFUNDED || this == PARTIALLY_REFUNDED;
    }
}
