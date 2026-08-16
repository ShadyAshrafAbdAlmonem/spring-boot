package com.inventory.inventory_management_system.sales.validator;

import com.inventory.inventory_management_system.sales.dto.request.CreateSalesOrderRequest;
import com.inventory.inventory_management_system.sales.entity.SalesOrder;
import org.springframework.stereotype.Component;

@Component
public class SalesValidator {

    public void validateCreate(CreateSalesOrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Sales order must contain at least one item");
        }
    }

    public void validateCancelable(SalesOrder order) {
        if ("COMPLETED".equals(order.getStatus())) {
            throw new IllegalStateException("Completed sales orders cannot be cancelled");
        }
    }
}
