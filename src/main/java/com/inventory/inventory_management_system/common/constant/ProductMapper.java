package com.inventory.inventory_management_system.common.constant;

import com.inventory.inventory_management_system.product.entity.Product; // Assuming this entity exists
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        return dto;
    }

    // You would also have a toEntity method for converting DTOs from requests
    // public Product toEntity(ProductDto dto) { ... }
}