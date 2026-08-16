package com.inventory.inventory_management_system.returnorder.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateReturnOrderRequest {

    @NotNull(message = "Sales Order ID is required")
    private Long salesOrderId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Refund amount is required")
    @Positive(message = "Refund amount must be positive")
    private BigDecimal refundAmount;

    @NotBlank(message = "Reason for return is required")
    private String reason;
}
