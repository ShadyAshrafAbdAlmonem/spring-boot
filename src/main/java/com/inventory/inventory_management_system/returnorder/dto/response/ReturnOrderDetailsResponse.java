package com.inventory.inventory_management_system.returnorder.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReturnOrderDetailsResponse extends ReturnOrderResponse {
    private String reason;
}
