package com.inventory.inventory_management_system.category.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDetailsResponse {
    private Long id;
    private String name;
    private String description;
    private CategoryResponse parent;
    private List<CategoryResponse> subCategories;
}
