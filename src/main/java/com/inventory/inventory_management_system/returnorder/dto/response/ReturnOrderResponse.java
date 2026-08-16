package com.inventory.inventory_management_system.returnorder.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReturnOrderResponse {
    private Long id;
    private String returnNumber;
    private Long salesOrderId;
    private Long customerId;
    private BigDecimal refundAmount;
    private String status;
    private LocalDateTime createdAt;
}
