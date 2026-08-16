package com.inventory.inventory_management_system.category.dto.request;

import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private String name;
    private String description;
    private Long parentId;
}