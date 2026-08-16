package com.inventory.inventory_management_system.stock.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferStockRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Source warehouse ID is required")
    private Long sourceWarehouseId;

    @NotNull(message = "Target warehouse ID is required")
    private Long targetWarehouseId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private Integer quantity;

    private String reason;
}