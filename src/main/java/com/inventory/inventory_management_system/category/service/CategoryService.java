package com.inventory.inventory_management_system.category.service;

import com.inventory.inventory_management_system.category.dto.request.CategoryFilterRequest;
import com.inventory.inventory_management_system.category.dto.request.CreateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.request.UpdateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.response.CategoryDetailsResponse;
import com.inventory.inventory_management_system.category.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface CategoryService {
    CategoryResponse createCategory(@NonNull CreateCategoryRequest request);
    CategoryDetailsResponse getCategoryById(@NonNull Long id);
    Page<CategoryResponse> getCategories(CategoryFilterRequest filterRequest, @NonNull Pageable pageable);
    CategoryResponse updateCategory(@NonNull Long id, @NonNull UpdateCategoryRequest request);
    void deleteCategory(@NonNull Long id);
}
