package com.inventory.inventory_management_system.report.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReportRequest extends ReportFilterRequest {
    private Long supplierId;
    private Long productId;
    private String status;
}