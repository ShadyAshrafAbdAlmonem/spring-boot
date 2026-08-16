package com.inventory.inventory_management_system.supplier.validator;

import com.inventory.inventory_management_system.supplier.dto.request.CreateSupplierRequest;
import com.inventory.inventory_management_system.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierValidator {

    private final SupplierRepository supplierRepository;

    public void validateCreate(CreateSupplierRequest request) {
        if (supplierRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }
    }
}