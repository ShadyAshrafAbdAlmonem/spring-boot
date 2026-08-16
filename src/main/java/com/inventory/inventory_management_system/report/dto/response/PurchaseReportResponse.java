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
public class PurchaseReportResponse {
    private long totalPurchaseOrders;
    private BigDecimal totalSpent;
    private List<SupplierPurchaseSummary> supplierSummaries;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplierPurchaseSummary {
        private Long supplierId;
        private String supplierName;
        private long orderCount;
        private BigDecimal totalAmount;
    }
}