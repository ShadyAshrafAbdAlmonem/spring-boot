package com.inventory.inventory_management_system.analytics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAnalyticsResponse {
    private long totalStockQuantity;
    private BigDecimal totalInventoryValue;
    private long lowStockAlertsCount;
    private long outOfStockCount;
    private double inventoryTurnoverRate;
}