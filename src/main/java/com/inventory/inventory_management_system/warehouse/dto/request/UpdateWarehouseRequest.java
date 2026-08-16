package com.inventory.inventory_management_system.warehouse.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateWarehouseRequest {

    @NotBlank(message = "Warehouse name is required")
    private String name;

    private String location;
    private String address;
    private String managerName;
    private String phone;
    private Boolean active;
}
