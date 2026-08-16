package com.inventory.inventory_management_system.category.validator;

import com.inventory.inventory_management_system.category.dto.request.CreateCategoryRequest;
import com.inventory.inventory_management_system.category.exception.CategoryAlreadyExistsException;
import com.inventory.inventory_management_system.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public void validateCreate(CreateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException("Category with name '" + request.getName() + "' already exists");
        }
    }
}