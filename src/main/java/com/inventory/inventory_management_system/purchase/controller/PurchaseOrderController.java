package com.inventory.inventory_management_system.purchase.controller;

import com.inventory.inventory_management_system.purchase.dto.request.CreatePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.dto.request.ReceivePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.dto.request.UpdatePurchaseStatusRequest;
import com.inventory.inventory_management_system.purchase.dto.response.PurchaseOrderResponse;
import com.inventory.inventory_management_system.purchase.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class PurchaseOrderController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> create(@Valid @RequestBody @NonNull CreatePurchaseOrderRequest request) {
        return new ResponseEntity<>(purchaseService.createPurchaseOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(purchaseService.getPurchaseOrderById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseOrderResponse>> getAll(
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        return ResponseEntity.ok(purchaseService.getPurchaseOrders(supplierId, status, pageable));
    }

    @PostMapping("/receive")
    public ResponseEntity<PurchaseOrderResponse> receiveItems(@Valid @RequestBody @NonNull ReceivePurchaseOrderRequest request) {
        return ResponseEntity.ok(purchaseService.receiveItems(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderResponse> updateStatus(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody UpdatePurchaseStatusRequest request) {
        return ResponseEntity.ok(purchaseService.updateStatus(id, request));
    }
}
