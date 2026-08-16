package com.inventory.inventory_management_system.shipment.service;

import com.inventory.inventory_management_system.shipment.dto.request.ShipmentRequest;
import com.inventory.inventory_management_system.shipment.dto.request.UpdateShipmentStatusRequest;
import com.inventory.inventory_management_system.shipment.dto.response.ShipmentResponse;
import org.springframework.lang.NonNull;

public interface ShipmentService {
    ShipmentResponse createShipment(@NonNull ShipmentRequest request);
    ShipmentResponse getShipmentById(@NonNull Long id);
    ShipmentResponse getShipmentByTrackingNumber(@NonNull String trackingNumber);
    ShipmentResponse updateStatus(@NonNull Long id, @NonNull UpdateShipmentStatusRequest request);
}
