package com.inventory.inventory_management_system.returnorder.validator;

import com.inventory.inventory_management_system.returnorder.dto.request.CreateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.entity.ReturnOrder;
import org.springframework.stereotype.Component;

@Component
public class ReturnOrderValidator {

    public void validateCreate(CreateReturnOrderRequest request) {
        if (request.getRefundAmount() == null || request.getRefundAmount().signum() <= 0) {
            throw new IllegalArgumentException("Refund amount must be positive");
        }
    }

    public void validateUpdate(ReturnOrder returnOrder, String newStatus) {
        if ("COMPLETED".equals(returnOrder.getStatus()) || "REJECTED".equals(returnOrder.getStatus())) {
            throw new IllegalStateException("Terminal return order state cannot be modified");
        }
    }
}
