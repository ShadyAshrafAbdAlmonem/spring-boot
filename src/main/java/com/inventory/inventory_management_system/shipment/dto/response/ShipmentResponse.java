package com.inventory.inventory_management_system.shipment.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShipmentResponse {
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String status;
    private String carrier;
    private LocalDateTime estimatedDelivery;
    private List<ShipmentTrackingResponse> trackingLogs;
}
