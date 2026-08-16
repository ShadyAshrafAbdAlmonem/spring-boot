package com.inventory.inventory_management_system.brand.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBrandRequest {

    @NotBlank(message = "Brand name is required")
    private String name;

    private String description;
    private String logoUrl;
}