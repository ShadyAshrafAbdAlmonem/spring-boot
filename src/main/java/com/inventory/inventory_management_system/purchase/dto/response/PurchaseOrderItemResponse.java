package com.inventory.inventory_management_system.purchase.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseOrderItemResponse {
    private Long id;
    private Long productId;
    private Integer orderedQuantity;
    private Integer receivedQuantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
