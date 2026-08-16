package com.inventory.inventory_management_system.sales.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateSalesOrderRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private String notes;

    @NotEmpty(message = "Sales order must contain items")
    @Valid
    private List<SalesOrderItemRequest> items;

    @Data
    public static class SalesOrderItemRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private Integer quantity;

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be positive")
        private BigDecimal unitPrice;
    }
}
