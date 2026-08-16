package com.inventory.inventory_management_system.inventoryadjustment.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryAdjustmentResponse {
    private Long id;
    private Long warehouseId;
    private Long productId;
    private Integer previousQuantity;
    private Integer adjustedQuantity;
    private Integer newQuantity;
    private String reason;
    private String note;
    private String adjustedBy;
    private LocalDateTime adjustedAt;
}
