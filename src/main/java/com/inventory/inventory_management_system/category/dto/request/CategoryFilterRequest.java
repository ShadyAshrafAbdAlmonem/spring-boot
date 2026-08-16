package com.inventory.inventory_management_system.category.dto.request;

import lombok.Data;

@Data
public class CategoryFilterRequest {
    private String name;
    private Long parentId;
}
