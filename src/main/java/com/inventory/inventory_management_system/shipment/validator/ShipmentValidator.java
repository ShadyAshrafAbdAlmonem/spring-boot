package com.inventory.inventory_management_system.shipment.validator;

import com.inventory.inventory_management_system.shipment.dto.request.ShipmentRequest;
import com.inventory.inventory_management_system.shipment.dto.request.UpdateShipmentStatusRequest;
import org.springframework.stereotype.Component;

@Component
public class ShipmentValidator {

    public void validateCreate(ShipmentRequest request) {
        if (request.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
    }

    public void validateStatusUpdate(UpdateShipmentStatusRequest request) {
        if (request.getStatus() == null || request.getStatus().isBlank()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
    }
}
