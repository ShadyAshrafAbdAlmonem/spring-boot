package com.inventory.inventory_management_system.coupon.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateCouponRequest {

    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal minOrderAmount;
    private Integer maxUses;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
}
