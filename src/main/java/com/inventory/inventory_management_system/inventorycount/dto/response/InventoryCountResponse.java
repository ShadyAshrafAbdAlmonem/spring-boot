package com.inventory.inventory_management_system.inventorycount.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryCountResponse {
    private Long id;
    private Long warehouseId;
    private String countReference;
    private String status;
    private String conductedBy;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String notes;
}
