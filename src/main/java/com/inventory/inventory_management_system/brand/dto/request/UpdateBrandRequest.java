package com.inventory.inventory_management_system.brand.dto.request;

import lombok.Data;

@Data
public class UpdateBrandRequest {

    private String name;
    private String description;
    private String logoUrl;
    private Boolean active;
}
