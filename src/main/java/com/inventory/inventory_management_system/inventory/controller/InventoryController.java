package com.inventory.inventory_management_system.inventory.controller;

import com.inventory.inventory_management_system.inventory.dto.request.InventorySearchRequest;
import com.inventory.inventory_management_system.inventory.dto.response.InventoryResponse;
import com.inventory.inventory_management_system.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/warehouse/{warehouseId}/product/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(
            @PathVariable @NonNull Long warehouseId,
            @PathVariable @NonNull Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByWarehouseAndProduct(warehouseId, productId));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<InventoryResponse>> searchInventory(
            @ModelAttribute InventorySearchRequest request,
            Pageable pageable) {
        return ResponseEntity.ok(inventoryService.searchInventory(request, pageable));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<Page<InventoryResponse>> getLowStockItems(
            @RequestParam(required = false) Long warehouseId,
            Pageable pageable) {
        return ResponseEntity.ok(inventoryService.getLowStockItems(warehouseId, pageable));
    }
}
