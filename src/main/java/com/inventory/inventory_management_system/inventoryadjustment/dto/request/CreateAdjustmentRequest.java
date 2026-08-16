package com.inventory.inventory_management_system.inventoryadjustment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAdjustmentRequest {

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Adjustment quantity change is required")
    private Integer adjustmentDelta; // Can be positive or negative

    @NotBlank(message = "Adjustment reason is required")
    private String reason;

    private String note;

    @NotBlank(message = "User making adjustment is required")
    private String adjustedBy;
}
