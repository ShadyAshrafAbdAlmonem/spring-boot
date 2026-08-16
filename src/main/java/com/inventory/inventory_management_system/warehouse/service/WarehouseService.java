package com.inventory.inventory_management_system.warehouse.service;

import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.warehouse.dto.request.CreateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.request.UpdateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.response.WarehouseResponse;
import org.springframework.lang.NonNull;

public interface WarehouseService {
    WarehouseResponse createWarehouse(@NonNull CreateWarehouseRequest request);
    WarehouseResponse updateWarehouse(@NonNull Long id, @NonNull UpdateWarehouseRequest request);
    WarehouseResponse getWarehouseById(@NonNull Long id);
    PageResponse<WarehouseResponse> getAllWarehouses(String search, Boolean active, int page, int size);
    void deleteWarehouse(@NonNull Long id);
}
