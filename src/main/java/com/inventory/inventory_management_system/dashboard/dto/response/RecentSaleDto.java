package com.inventory.inventory_management_system.dashboard.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RecentSaleDto {
    private Long orderId;
    private String orderNumber;
    private String customerName;
    private BigDecimal amount;
    private LocalDateTime date;
}
