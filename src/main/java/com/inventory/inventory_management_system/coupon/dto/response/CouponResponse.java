package com.inventory.inventory_management_system.coupon.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponResponse {
    private Long id;
    private String code;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal minOrderAmount;
    private Integer maxUses;
    private Integer currentUses;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
}
