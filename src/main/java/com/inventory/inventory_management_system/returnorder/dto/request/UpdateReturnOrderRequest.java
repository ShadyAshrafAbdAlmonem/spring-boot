package com.inventory.inventory_management_system.returnorder.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateReturnOrderRequest {

    @NotBlank(message = "Status is required")
    private String status;

    private String remarks;
}
