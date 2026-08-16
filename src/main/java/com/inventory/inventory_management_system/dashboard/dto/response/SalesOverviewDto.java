package com.inventory.inventory_management_system.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SalesOverviewDto {
    private BigDecimal todaySales;
    private BigDecimal weeklySales;
    private BigDecimal monthlySales;
}
