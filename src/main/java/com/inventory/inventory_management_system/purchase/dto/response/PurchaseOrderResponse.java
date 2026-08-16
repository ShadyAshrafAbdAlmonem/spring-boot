package com.inventory.inventory_management_system.purchase.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PurchaseOrderResponse {
    private Long id;
    private String poNumber;
    private Long supplierId;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private BigDecimal totalAmount;
    private String status;
}
