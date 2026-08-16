package com.inventory.inventory_management_system.shipment.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShipmentTrackingResponse {
    private Long id;
    private String status;
    private String location;
    private String remarks;
    private LocalDateTime timestamp;
}