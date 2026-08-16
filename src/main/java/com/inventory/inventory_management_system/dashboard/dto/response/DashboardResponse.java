package com.inventory.inventory_management_system.dashboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private DashboardSummaryDto summary;
    private SalesOverviewDto salesOverview;
    private InventoryOverviewDto inventoryOverview;
    private RevenueChartDto revenueChart;
    private List<RecentSaleDto> recentSales;
    private List<TopProductDto> topProducts;
    private List<LowStockDto> lowStockAlerts;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DashboardSummaryDto {
        private BigDecimal totalRevenue;
        private Long totalOrders;
        private Long totalCustomers;
        private Long lowStockCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalesOverviewDto {
        private BigDecimal todaySales;
        private BigDecimal weeklySales;
        private BigDecimal monthlySales;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryOverviewDto {
        private Long totalItems;
        private Long inStockCount;
        private Long outOfStockCount;
        private Long lowStockCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevenueChartDto {
        private List<String> labels;
        private List<BigDecimal> data;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentSaleDto {
        private Long orderId;
        private String orderNumber;
        private String customerName;
        private BigDecimal amount;
        private LocalDateTime date;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopProductDto {
        private Long productId;
        private String productName;
        private Long totalQuantitySold;
        private BigDecimal totalRevenue;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LowStockDto {
        private Long productId;
        private String productName;
        private String sku;
        private int currentStock;
        private int threshold;
    }
}