package com.inventory.inventory_management_system.returnorder.dto.request;

import lombok.Data;

@Data
public class ReturnOrderFilterRequest {
    private Long customerId;
    private Long salesOrderId;
    private String status;
}
