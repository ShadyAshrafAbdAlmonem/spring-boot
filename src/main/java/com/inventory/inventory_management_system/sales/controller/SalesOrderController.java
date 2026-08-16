package com.inventory.inventory_management_system.sales.controller;

import com.inventory.inventory_management_system.sales.dto.request.CancelSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.request.CompleteSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.request.CreateSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.response.SalesOrderResponse;
import com.inventory.inventory_management_system.sales.service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/sales-orders")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class SalesOrderController {

    private final SalesService salesService;

    @PostMapping
    public ResponseEntity<SalesOrderResponse> createOrder(@Valid @RequestBody @NonNull CreateSalesOrderRequest request) {
        return new ResponseEntity<>(salesService.createSalesOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(salesService.getSalesOrderById(id));
    }

    @PostMapping("/complete")
    public ResponseEntity<SalesOrderResponse> completeOrder(@Valid @RequestBody @NonNull CompleteSalesOrderRequest request) {
        return ResponseEntity.ok(salesService.completeSalesOrder(request));
    }

    @PostMapping("/cancel")
    public ResponseEntity<SalesOrderResponse> cancelOrder(@Valid @RequestBody @NonNull CancelSalesOrderRequest request) {
        return ResponseEntity.ok(salesService.cancelSalesOrder(request));
    }

    @GetMapping
    public ResponseEntity<Page<SalesOrderResponse>> filterOrders(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String status,
            Pageable pageable) {
        return ResponseEntity.ok(salesService.filterSalesOrders(customerId, status, pageable));
    }
}