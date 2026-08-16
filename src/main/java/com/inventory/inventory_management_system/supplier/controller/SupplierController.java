package com.inventory.inventory_management_system.supplier.controller;

import com.inventory.inventory_management_system.supplier.dto.request.CreateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.request.UpdateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.response.SupplierResponse;
import com.inventory.inventory_management_system.supplier.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponse> create(@Valid @RequestBody @NonNull CreateSupplierRequest request) {
        return new ResponseEntity<>(supplierService.createSupplier(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SupplierResponse>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        return ResponseEntity.ok(supplierService.searchSuppliers(name, active, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> update(@PathVariable @NonNull Long id, @RequestBody @NonNull UpdateSupplierRequest request) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}