package com.inventory.inventory_management_system.analytics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesAnalyticsResponse {
    private BigDecimal totalSalesValue;
    private long totalCompletedOrders;
    private BigDecimal averageOrderValue;
    private String topSellingProduct;
    private Map<String, BigDecimal> salesByCategory;
    private DashboardChartResponse monthlySalesTrend;
}