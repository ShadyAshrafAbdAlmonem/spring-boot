package com.inventory.inventory_management_system.invoice.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceDetailsResponse extends InvoiceResponse {
    private String notes;
    private List<InvoiceItemResponse> items;

    @Data
    public static class InvoiceItemResponse {
        private Long id;
        private Long productId;
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
}
