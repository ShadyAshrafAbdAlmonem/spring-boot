package com.inventory.inventory_management_system.productimage.dto.response;

import lombok.Data;

@Data
public class ProductImageResponse {
    private Long id;
    private Long productId;
    private String imageUrl;
    private Boolean isPrimary;
}
