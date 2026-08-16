package com.inventory.inventory_management_system.brand.validator;

import com.inventory.inventory_management_system.brand.dto.request.CreateBrandRequest;
import com.inventory.inventory_management_system.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandValidator {

    private final BrandRepository brandRepository;

    public void validateCreate(CreateBrandRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Brand name already exists: " + request.getName());
        }
    }
}