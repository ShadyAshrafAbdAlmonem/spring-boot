package com.inventory.inventory_management_system.discount.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApplyDiscountRequest {

    @NotNull(message = "Discount ID is required")
    private Long discountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal originalAmount;
}