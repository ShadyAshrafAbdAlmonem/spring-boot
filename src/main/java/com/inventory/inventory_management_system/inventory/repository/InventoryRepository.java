package com.inventory.inventory_management_system.inventory.repository;

import com.inventory.inventory_management_system.inventory.dto.response.InventoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InventoryRepository {

    // Simulating database storage / JPA methods for read-only inventory operations
    public Optional<InventoryResponse> findByWarehouseIdAndProductId(Long warehouseId, Long productId) {
        return Optional.empty();
    }

    public Page<InventoryResponse> searchInventory(Long warehouseId, Long productId, String sku, @NonNull Pageable pageable) {
        return Page.empty(pageable);
    }
}