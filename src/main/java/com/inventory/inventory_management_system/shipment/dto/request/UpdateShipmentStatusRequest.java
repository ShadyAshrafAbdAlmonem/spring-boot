package com.inventory.inventory_management_system.shipment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateShipmentStatusRequest {

    @NotBlank(message = "Status is required")
    private String status;

    private String location;
    private String remarks;
}
