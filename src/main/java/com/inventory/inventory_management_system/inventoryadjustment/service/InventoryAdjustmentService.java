package com.inventory.inventory_management_system.inventoryadjustment.service;

import com.inventory.inventory_management_system.inventoryadjustment.dto.request.CreateAdjustmentRequest;
import com.inventory.inventory_management_system.inventoryadjustment.dto.response.InventoryAdjustmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
 
public interface InventoryAdjustmentService {
    InventoryAdjustmentResponse createAdjustment(@NonNull CreateAdjustmentRequest request);
    InventoryAdjustmentResponse getAdjustmentById(@NonNull Long id);
    Page<InventoryAdjustmentResponse> getAdjustmentsByWarehouse(Long warehouseId, Pageable pageable);
    Page<InventoryAdjustmentResponse> getAdjustmentsByProduct(Long productId, Pageable pageable);
}
