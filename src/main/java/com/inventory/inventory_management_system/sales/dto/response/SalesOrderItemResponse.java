package com.inventory.inventory_management_system.sales.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesOrderItemResponse {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
