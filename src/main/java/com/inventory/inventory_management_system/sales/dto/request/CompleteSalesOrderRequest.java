package com.inventory.inventory_management_system.sales.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompleteSalesOrderRequest {

    @NotNull(message = "Sales Order ID is required")
    private Long orderId;

    private String paymentReference;
    private String notes;
}
