package com.inventory.inventory_management_system.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsDto {
    private Double conversionRate;
    private Double averageOrderValue;
}
