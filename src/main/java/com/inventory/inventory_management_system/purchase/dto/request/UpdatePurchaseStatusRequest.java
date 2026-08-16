package com.inventory.inventory_management_system.purchase.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePurchaseStatusRequest {

    @NotBlank(message = "Status is required")
    private String status;

    private String reason;
}