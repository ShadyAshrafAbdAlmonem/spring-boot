package com.inventory.inventory_management_system.analytics.service;

import com.inventory.inventory_management_system.analytics.dto.response.DashboardChartResponse;
import com.inventory.inventory_management_system.analytics.dto.response.InventoryAnalyticsResponse;
import com.inventory.inventory_management_system.analytics.dto.response.SalesAnalyticsResponse;

public interface AnalyticsService {
    SalesAnalyticsResponse getSalesAnalytics();
    InventoryAnalyticsResponse getInventoryAnalytics();
    DashboardChartResponse getDashboardChartData();
}