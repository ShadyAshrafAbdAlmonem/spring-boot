package com.inventory.inventory_management_system.common.enums;

/**
 * Shipment status enumeration for tracking shipment states.
 */
public enum ShipmentStatus {
    
    PENDING("Pending", "Shipment is pending"),
    PROCESSING("Processing", "Shipment is being processed"),
    PICKED_UP("Picked Up", "Package has been picked up"),
    IN_TRANSIT("In Transit", "Package is in transit"),
    OUT_FOR_DELIVERY("Out for Delivery", "Package is out for delivery"),
    DELIVERED("Delivered", "Package delivered successfully"),
    FAILED_DELIVERY("Failed Delivery", "Delivery attempt failed"),
    RETURNED("Returned", "Package returned to sender"),
    CANCELLED("Cancelled", "Shipment was cancelled"),
    ON_HOLD("On Hold", "Shipment is on hold"),
    LOST("Lost", "Package is lost"),
    DAMAGED("Damaged", "Package was damaged");

    private final String displayName;
    private final String description;

    ShipmentStatus(String displayName, String description) {
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
     * Check if the status indicates successful delivery
     */
    public boolean isDelivered() {
        return this == DELIVERED;
    }

    /**
     * Check if the status indicates shipment failure
     */
    public boolean isFailed() {
        return this == FAILED_DELIVERY || this == LOST || this == CANCELLED;
    }

    /**
     * Check if the status indicates shipment is in progress
     */
    public boolean isInProgress() {
        return this == PENDING || this == PROCESSING || this == PICKED_UP || 
               this == IN_TRANSIT || this == OUT_FOR_DELIVERY || this == ON_HOLD;
    }

    /**
     * Check if the status indicates an active shipment (not final state)
     */
    public boolean isActive() {
        return this != DELIVERED && this != FAILED_DELIVERY && 
               this != RETURNED && this != CANCELLED && this != LOST;
    }
}
