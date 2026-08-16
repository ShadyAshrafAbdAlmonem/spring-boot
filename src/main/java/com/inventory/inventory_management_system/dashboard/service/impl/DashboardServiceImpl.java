package com.inventory.inventory_management_system.dashboard.service.impl;

import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse;
import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse.DashboardSummaryDto;
import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse.InventoryOverviewDto;
import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse.LowStockDto;
import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse.RecentSaleDto;
import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse.RevenueChartDto;
import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse.SalesOverviewDto;
import com.inventory.inventory_management_system.dashboard.dto.response.DashboardResponse.TopProductDto;
import com.inventory.inventory_management_system.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    @Override
    public DashboardResponse getDashboardData() {
        return DashboardResponse.builder()
                .summary(DashboardSummaryDto.builder()
                        .totalRevenue(new BigDecimal("125000.00"))
                        .totalOrders(1240L)
                        .totalCustomers(350L)
                        .lowStockCount(5L)
                        .build())
                .salesOverview(SalesOverviewDto.builder()
                        .todaySales(new BigDecimal("1200.00"))
                        .weeklySales(new BigDecimal("15000.00"))
                        .monthlySales(new BigDecimal("45000.00"))
                        .build())
                .inventoryOverview(InventoryOverviewDto.builder()
                        .totalItems(500L)
                        .inStockCount(480L)
                        .outOfStockCount(15L)
                        .lowStockCount(5L)
                        .build())
                .revenueChart(RevenueChartDto.builder()
                        .labels(List.of("Jan", "Feb", "Mar", "Apr", "May"))
                        .data(List.of(new BigDecimal("10000"), new BigDecimal("15000"), new BigDecimal("20000"), new BigDecimal("18000"), new BigDecimal("25000")))
                        .build())
                .recentSales(List.of(
                        RecentSaleDto.builder()
                                .orderId(101L)
                                .orderNumber("SO-001")
                                .customerName("John Doe")
                                .amount(new BigDecimal("250.00"))
                                .date(LocalDateTime.now())
                                .build()
                ))
                .topProducts(List.of(
                        TopProductDto.builder()
                                .productId(1L)
                                .productName("Wooden Chair")
                                .totalQuantitySold(120L)
                                .totalRevenue(new BigDecimal("6000.00"))
                                .build()
                ))
                .lowStockAlerts(List.of(
                        LowStockDto.builder()
                                .productId(2L)
                                .productName("Dining Table")
                                .sku("TBL-001")
                                .currentStock(2)
                                .threshold(5)
                                .build()
                ))
                .build();
    }
}
