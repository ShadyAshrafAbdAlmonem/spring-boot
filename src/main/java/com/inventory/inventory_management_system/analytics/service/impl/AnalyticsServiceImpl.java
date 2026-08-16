package com.inventory.inventory_management_system.analytics.service.impl;

import com.inventory.inventory_management_system.analytics.dto.response.DashboardChartResponse;
import com.inventory.inventory_management_system.analytics.dto.response.InventoryAnalyticsResponse;
import com.inventory.inventory_management_system.analytics.dto.response.SalesAnalyticsResponse;
import com.inventory.inventory_management_system.analytics.repository.AnalyticsRepository;
import com.inventory.inventory_management_system.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Override
    public SalesAnalyticsResponse getSalesAnalytics() {
        BigDecimal totalSales = analyticsRepository.fetchTotalSales();

        return SalesAnalyticsResponse.builder()
                .totalSalesValue(totalSales)
                .totalCompletedOrders(0)
                .averageOrderValue(BigDecimal.ZERO)
                .topSellingProduct("N/A")
                .salesByCategory(Collections.emptyMap())
                .build();
    }

    @Override
    public InventoryAnalyticsResponse getInventoryAnalytics() {
        long lowStockCount = analyticsRepository.fetchLowStockCount();

        return InventoryAnalyticsResponse.builder()
                .totalStockQuantity(0)
                .totalInventoryValue(BigDecimal.ZERO)
                .lowStockAlertsCount(lowStockCount)
                .outOfStockCount(0)
                .inventoryTurnoverRate(0.0)
                .build();
    }

    @Override
    public DashboardChartResponse getDashboardChartData() {
        return DashboardChartResponse.builder()
                .labels(List.of("Jan", "Feb", "Mar", "Apr", "May"))
                .datasets(List.of(
                        DashboardChartResponse.ChartDataset.builder()
                                .label("Revenue")
                                .data(List.of(12000.0, 19000.0, 15000.0, 22000.0, 30000.0))
                                .build()
                ))
                .build();
    }
}