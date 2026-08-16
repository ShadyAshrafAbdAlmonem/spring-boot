package com.inventory.inventory_management_system.purchase.validator;

import com.inventory.inventory_management_system.purchase.dto.request.CreatePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

@Component
public class PurchaseValidator {

    public void validateCreate(CreatePurchaseOrderRequest request) {
        if (request.getExpectedDeliveryDate() != null && request.getExpectedDeliveryDate().isBefore(request.getOrderDate())) {
            throw new IllegalArgumentException("Expected delivery date cannot be before order date");
        }
    }

    public void validateStatusTransition(PurchaseOrder po, String newStatus) {
        if ("CANCELLED".equals(po.getStatus())) {
            throw new IllegalStateException("Cannot update status of a cancelled purchase order");
        }
    }
}