package com.inventory.inventory_management_system.product.validator;

import com.inventory.inventory_management_system.common.exception.BadRequestException;
import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.product.dto.request.CreateProductRequest;
import com.inventory.inventory_management_system.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;

    public void validateCreate(CreateProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Product SKU already exists: " + request.getSku());
        }
        if (request.getBarcode() != null && productRepository.existsByBarcode(request.getBarcode())) {
            throw new DuplicateResourceException("Product Barcode already exists: " + request.getBarcode());
        }
    }

    public void validateStockAdjustment(Integer currentQuantity, Integer delta) {
        if (currentQuantity + delta < 0) {
            throw new BadRequestException("Insufficient stock. Cannot deduct " + Math.abs(delta) + " items.");
        }
    }
}
