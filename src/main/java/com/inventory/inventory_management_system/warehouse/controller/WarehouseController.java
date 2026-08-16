package com.inventory.inventory_management_system.warehouse.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.warehouse.dto.request.CreateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.request.UpdateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.response.WarehouseResponse;
import com.inventory.inventory_management_system.warehouse.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<ApiResponse<WarehouseResponse>> createWarehouse(@Valid @RequestBody @NonNull CreateWarehouseRequest request) {
        WarehouseResponse created = warehouseService.createWarehouse(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Warehouse created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<WarehouseResponse>>> getAllWarehouses(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getAllWarehouses(search, active, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WarehouseResponse>> getWarehouseById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getWarehouseById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WarehouseResponse>> updateWarehouse(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull UpdateWarehouseRequest request) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.updateWarehouse(id, request), "Warehouse updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWarehouse(@PathVariable @NonNull Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Warehouse deleted successfully"));
    }
}
