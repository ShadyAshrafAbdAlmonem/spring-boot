package com.inventory.inventory_management_system.shipment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShipmentRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotBlank(message = "Carrier is required")
    private String carrier;

    private LocalDateTime estimatedDelivery;
}
