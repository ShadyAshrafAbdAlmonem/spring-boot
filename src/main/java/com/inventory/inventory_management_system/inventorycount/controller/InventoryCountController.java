package com.inventory.inventory_management_system.inventorycount.controller;

import com.inventory.inventory_management_system.inventorycount.dto.request.CreateInventoryCountRequest;
import com.inventory.inventory_management_system.inventorycount.dto.response.InventoryCountResponse;
import com.inventory.inventory_management_system.inventorycount.service.InventoryCountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/inventory-counts")
@RequiredArgsConstructor
public class InventoryCountController {

    private final InventoryCountService countService;

    @PostMapping
    public ResponseEntity<InventoryCountResponse> createCountSession(@Valid @RequestBody @NonNull CreateInventoryCountRequest request) {
        return new ResponseEntity<>(countService.createCountSession(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryCountResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(countService.getCountById(id));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<Page<InventoryCountResponse>> getByWarehouse(@PathVariable Long warehouseId, Pageable pageable) {
        return ResponseEntity.ok(countService.getCountsByWarehouse(warehouseId, pageable));
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<InventoryCountResponse> startSession(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(countService.startCountSession(id));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<InventoryCountResponse> completeSession(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(countService.completeCountSession(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<InventoryCountResponse> cancelSession(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(countService.cancelCountSession(id));
    }
}
