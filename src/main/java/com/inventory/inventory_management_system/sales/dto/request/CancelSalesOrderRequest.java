package com.inventory.inventory_management_system.sales.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CancelSalesOrderRequest {

    @NotNull(message = "Sales Order ID is required")
    private Long orderId;

    @NotBlank(message = "Reason for cancellation is required")
    private String reason;
}
