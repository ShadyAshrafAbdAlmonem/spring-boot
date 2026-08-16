package com.inventory.inventory_management_system.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryOverviewDto {
    private Long totalItems;
    private Long inStockCount;
    private Long outOfStockCount;
    private Long lowStockCount;
}
