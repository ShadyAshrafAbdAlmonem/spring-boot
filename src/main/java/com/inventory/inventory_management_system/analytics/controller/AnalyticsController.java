package com.inventory.inventory_management_system.analytics.controller;

import com.inventory.inventory_management_system.analytics.dto.response.DashboardChartResponse;
import com.inventory.inventory_management_system.analytics.dto.response.InventoryAnalyticsResponse;
import com.inventory.inventory_management_system.analytics.dto.response.SalesAnalyticsResponse;
import com.inventory.inventory_management_system.analytics.service.AnalyticsService;
import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<SalesAnalyticsResponse>> getSalesAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getSalesAnalytics()));
    }

    @GetMapping("/inventory")
    public ResponseEntity<ApiResponse<InventoryAnalyticsResponse>> getInventoryAnalytics() {
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getInventoryAnalytics()));
    }

    @GetMapping("/dashboard-chart")
    public ResponseEntity<ApiResponse<DashboardChartResponse>> getDashboardChartData() {
        return ResponseEntity.ok(ApiResponse.success(analyticsService.getDashboardChartData()));
    }
}