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
public class SalesReportResponse {
    private long totalOrders;
    private long totalItemsSold;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
    private List<SalesItemSummary> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesItemSummary {
        private Long productId;
        private String productName;
        private long quantitySold;
        private BigDecimal totalAmount;
    }
}