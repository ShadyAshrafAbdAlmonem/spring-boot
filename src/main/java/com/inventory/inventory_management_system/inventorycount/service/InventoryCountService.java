package com.inventory.inventory_management_system.inventorycount.service;

import com.inventory.inventory_management_system.inventorycount.dto.request.CreateInventoryCountRequest;
import com.inventory.inventory_management_system.inventorycount.dto.response.InventoryCountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
 
public interface InventoryCountService {
    InventoryCountResponse createCountSession(@NonNull CreateInventoryCountRequest request);
    InventoryCountResponse getCountById(@NonNull Long id);
    Page<InventoryCountResponse> getCountsByWarehouse(Long warehouseId, Pageable pageable);
    InventoryCountResponse startCountSession(@NonNull Long id);
    InventoryCountResponse completeCountSession(@NonNull Long id);
    InventoryCountResponse cancelCountSession(@NonNull Long id);
}
