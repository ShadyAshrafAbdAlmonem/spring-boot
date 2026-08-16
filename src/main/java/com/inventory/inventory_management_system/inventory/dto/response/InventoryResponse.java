package com.inventory.inventory_management_system.inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private Long id;
    private Long productId;
    private String productName;
    private String sku;
    private Long warehouseId;
    private String warehouseName;
    private Integer quantityOnHand;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private Integer reorderLevel;
    private LocalDateTime lastUpdated;
}
