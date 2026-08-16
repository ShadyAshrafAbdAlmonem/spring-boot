package com.inventory.inventory_management_system.category.controller;

import com.inventory.inventory_management_system.category.dto.request.CategoryFilterRequest;
import com.inventory.inventory_management_system.category.dto.request.CreateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.request.UpdateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.response.CategoryDetailsResponse;
import com.inventory.inventory_management_system.category.dto.response.CategoryResponse;
import com.inventory.inventory_management_system.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody @NonNull CreateCategoryRequest request) {
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDetailsResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAll(
            CategoryFilterRequest filterRequest,
            Pageable pageable) {
        return ResponseEntity.ok(categoryService.getCategories(filterRequest, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull UpdateCategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
