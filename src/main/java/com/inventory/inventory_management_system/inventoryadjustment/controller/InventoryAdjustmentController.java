package com.inventory.inventory_management_system.inventoryadjustment.controller;

import com.inventory.inventory_management_system.inventoryadjustment.dto.request.CreateAdjustmentRequest;
import com.inventory.inventory_management_system.inventoryadjustment.dto.response.InventoryAdjustmentResponse;
import com.inventory.inventory_management_system.inventoryadjustment.service.InventoryAdjustmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/inventory-adjustments")
@RequiredArgsConstructor
public class InventoryAdjustmentController {

    private final InventoryAdjustmentService adjustmentService;

    @PostMapping
    public ResponseEntity<InventoryAdjustmentResponse> createAdjustment(@Valid @RequestBody @NonNull CreateAdjustmentRequest request) {
        return new ResponseEntity<>(adjustmentService.createAdjustment(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryAdjustmentResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(adjustmentService.getAdjustmentById(id));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<Page<InventoryAdjustmentResponse>> getByWarehouse(@PathVariable Long warehouseId, Pageable pageable) {
        return ResponseEntity.ok(adjustmentService.getAdjustmentsByWarehouse(warehouseId, pageable));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Page<InventoryAdjustmentResponse>> getByProduct(@PathVariable Long productId, Pageable pageable) {
        return ResponseEntity.ok(adjustmentService.getAdjustmentsByProduct(productId, pageable));
    }
}
