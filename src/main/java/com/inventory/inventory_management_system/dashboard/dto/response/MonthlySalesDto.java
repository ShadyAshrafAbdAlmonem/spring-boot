package com.inventory.inventory_management_system.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MonthlySalesDto {
    private String month;
    private BigDecimal totalSales;
    private Long orderCount;
}
