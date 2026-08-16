package com.inventory.inventory_management_system.inventory.service.impl;

import com.inventory.inventory_management_system.inventory.dto.request.InventorySearchRequest;
import com.inventory.inventory_management_system.inventory.dto.response.InventoryResponse;
import com.inventory.inventory_management_system.inventory.repository.InventoryRepository;
import com.inventory.inventory_management_system.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@SuppressWarnings("null")
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse getInventoryByWarehouseAndProduct(Long warehouseId, Long productId) {
        return inventoryRepository.findByWarehouseIdAndProductId(warehouseId, productId)
                .orElseThrow(() -> new RuntimeException("Inventory record not found for warehouse " + warehouseId + " and product " + productId));
    }

    @Override
    public Page<InventoryResponse> searchInventory(InventorySearchRequest searchRequest, Pageable pageable) {
        return inventoryRepository.searchInventory(
                searchRequest.getWarehouseId(),
                searchRequest.getProductId(),
                searchRequest.getSku(),
                pageable
        );
    }

    @Override
    public Page<InventoryResponse> getLowStockItems(Long warehouseId, Pageable pageable) {
        return inventoryRepository.searchInventory(warehouseId, null, null, pageable);
    }
}
