package com.inventory.inventory_management_system.sales.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SalesOrderResponse {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private BigDecimal totalAmount;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    private List<SalesOrderItemResponse> items;
}
