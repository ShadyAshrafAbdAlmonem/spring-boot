package com.inventory.inventory_management_system.brand.dto.response;

import lombok.Data;

@Data
public class BrandResponse {

    private Long id;
    private String name;
    private String description;
    private String logoUrl;
    private Boolean active;
}
