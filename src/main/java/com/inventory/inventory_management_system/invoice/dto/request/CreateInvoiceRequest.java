package com.inventory.inventory_management_system.invoice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateInvoiceRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private String notes;

    @NotEmpty(message = "Invoice must contain at least one item")
    @Valid
    private List<InvoiceItemRequest> items;

    @Data
    public static class InvoiceItemRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotBlank(message = "Description is required")
        private String description;

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private Integer quantity;

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be positive")
        private BigDecimal unitPrice;
    }
}