package com.inventory.inventory_management_system.report.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierReportResponse {
    private Long supplierId;
    private String supplierName;
    private long totalPurchaseOrders;
    private BigDecimal totalPurchasedValue;
}