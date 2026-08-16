package com.inventory.inventory_management_system.inventorycount.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateInventoryCountRequest {

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotBlank(message = "Count reference code is required")
    private String countReference;

    private String conductedBy;

    private String notes;
}
