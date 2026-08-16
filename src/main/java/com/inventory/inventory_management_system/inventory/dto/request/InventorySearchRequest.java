package com.inventory.inventory_management_system.inventory.dto.request;

import lombok.Data;

@Data
public class InventorySearchRequest {
    private Long warehouseId;
    private Long productId;
    private String sku;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Boolean lowStockOnly;
}
