package com.inventory.inventory_management_system.category.service.impl;

import com.inventory.inventory_management_system.category.dto.request.CategoryFilterRequest;
import com.inventory.inventory_management_system.category.dto.request.CreateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.request.UpdateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.response.CategoryDetailsResponse;
import com.inventory.inventory_management_system.category.dto.response.CategoryResponse;
import com.inventory.inventory_management_system.category.entity.Category;
import com.inventory.inventory_management_system.category.exception.CategoryNotFoundException;
import com.inventory.inventory_management_system.category.mapper.CategoryMapper;
import com.inventory.inventory_management_system.category.repository.CategoryRepository;
import com.inventory.inventory_management_system.category.service.CategoryService;
import com.inventory.inventory_management_system.category.specification.CategorySpecification;
import com.inventory.inventory_management_system.category.validator.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator categoryValidator;

    @Override
    public CategoryResponse createCategory(@NonNull CreateCategoryRequest request) {
        categoryValidator.validateCreate(request);

        Category category = categoryMapper.toEntity(request);

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException("Parent category not found: " + request.getParentId()));
            category.setParent(parent);
        }

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDetailsResponse getCategoryById(@NonNull Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));
        return categoryMapper.toDetailsResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getCategories(CategoryFilterRequest filterRequest, @NonNull Pageable pageable) {
        return categoryRepository.findAll(CategorySpecification.filter(filterRequest), pageable)
                .map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse updateCategory(@NonNull Long id, @NonNull UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + id));

        categoryMapper.updateEntityFromDto(request, category);

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new CategoryNotFoundException("Parent category not found: " + request.getParentId()));
            category.setParent(parent);
        }

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(@NonNull Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
