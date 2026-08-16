package com.inventory.inventory_management_system.purchase.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class ReceivePurchaseOrderRequest {

    @NotNull(message = "Purchase Order ID is required")
    private Long purchaseOrderId;

    @NotEmpty(message = "Received item breakdown is required")
    @Valid
    private List<ReceivedItemRequest> items;

    @Data
    public static class ReceivedItemRequest {
        @NotNull(message = "Item ID is required")
        private Long itemId;

        @NotNull(message = "Received quantity is required")
        @PositiveOrZero(message = "Quantity cannot be negative")
        private Integer quantityReceived;
    }
}
