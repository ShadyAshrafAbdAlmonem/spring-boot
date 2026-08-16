package com.inventory.inventory_management_system.analytics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardChartResponse {
    private List<String> labels; // e.g., ["Jan", "Feb", "Mar"]
    private List<ChartDataset> datasets;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChartDataset {
        private String label; // e.g., "Revenue 2026"
        private List<Double> data;
    }
}