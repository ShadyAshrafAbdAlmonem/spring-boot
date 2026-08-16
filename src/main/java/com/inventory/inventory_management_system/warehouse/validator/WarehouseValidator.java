package com.inventory.inventory_management_system.warehouse.validator;

import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.warehouse.dto.request.CreateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseValidator {

    private final WarehouseRepository warehouseRepository;

    public void validateCreate(CreateWarehouseRequest request) {
        if (warehouseRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Warehouse name already exists: " + request.getName());
        }
        if (warehouseRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Warehouse code already exists: " + request.getCode());
        }
    }
}