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
public class CustomerReportResponse {
    private Long customerId;
    private String customerName;
    private String email;
    private long totalOrders;
    private BigDecimal totalSpent;
}