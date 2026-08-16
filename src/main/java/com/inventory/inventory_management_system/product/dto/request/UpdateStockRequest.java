package com.inventory.inventory_management_system.product.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStockRequest {

    @NotNull(message = "Quantity difference is required")
    private Integer quantityDelta; // موجب للإضافة (Stock In)، سالب للخصم (Stock Out)

    private String reason; // e.g., "MANUAL_ADJUSTMENT", "DAMAGE", "COUNT"
}
