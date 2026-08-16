package com.inventory.inventory_management_system.returnorder.controller;

import com.inventory.inventory_management_system.returnorder.dto.request.CreateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.dto.request.ReturnOrderFilterRequest;
import com.inventory.inventory_management_system.returnorder.dto.request.UpdateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderDetailsResponse;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderResponse;
import com.inventory.inventory_management_system.returnorder.service.ReturnOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/return-orders")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ReturnOrderController {

    private final ReturnOrderService returnOrderService;

    @PostMapping
    public ResponseEntity<ReturnOrderDetailsResponse> create(@Valid @RequestBody @NonNull CreateReturnOrderRequest request) {
        return new ResponseEntity<>(returnOrderService.createReturnOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnOrderDetailsResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(returnOrderService.getReturnOrderById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReturnOrderDetailsResponse> updateStatus(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull UpdateReturnOrderRequest request) {
        return ResponseEntity.ok(returnOrderService.updateReturnOrderStatus(id, request));
    }

    @GetMapping
    public ResponseEntity<Page<ReturnOrderResponse>> filter(
            @ModelAttribute ReturnOrderFilterRequest filter,
            Pageable pageable) {
        return ResponseEntity.ok(returnOrderService.filterReturnOrders(filter, pageable));
    }
}
