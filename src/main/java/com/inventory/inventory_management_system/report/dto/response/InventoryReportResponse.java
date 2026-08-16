package com.inventory.inventory_management_system.report.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReportResponse {
    private long totalProducts;
    private long totalQuantityInStock;
    private long lowStockCount;
    private long outOfStockCount;
    private BigDecimal totalInventoryValue;
    private List<CategoryStockSummary> categories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryStockSummary {
        private Long categoryId;
        private String categoryName;
        private long productCount;
        private long totalQuantity;
    }
}