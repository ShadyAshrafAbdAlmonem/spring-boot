package com.inventory.inventory_management_system.discount.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiscountResponse {
    private Long id;
    private String name;
    private String description;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal minPurchaseAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
}
