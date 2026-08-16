package com.inventory.inventory_management_system.inventory.service;

import com.inventory.inventory_management_system.inventory.dto.request.InventorySearchRequest;
import com.inventory.inventory_management_system.inventory.dto.response.InventoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
 
public interface InventoryService {
    InventoryResponse getInventoryByWarehouseAndProduct(@NonNull Long warehouseId, @NonNull Long productId);
    Page<InventoryResponse> searchInventory(InventorySearchRequest searchRequest, @NonNull Pageable pageable);
    Page<InventoryResponse> getLowStockItems(Long warehouseId, @NonNull Pageable pageable);
}
