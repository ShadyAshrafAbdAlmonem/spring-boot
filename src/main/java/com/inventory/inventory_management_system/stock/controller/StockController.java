package com.inventory.inventory_management_system.stock.controller;

import com.inventory.inventory_management_system.stock.dto.request.AddStockRequest;
import com.inventory.inventory_management_system.stock.dto.request.RemoveStockRequest;
import com.inventory.inventory_management_system.stock.dto.request.TransferStockRequest;
import com.inventory.inventory_management_system.stock.dto.response.StockResponse;
import com.inventory.inventory_management_system.stock.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<StockResponse> addStock(@Valid @RequestBody AddStockRequest request) {
        return ResponseEntity.ok(stockService.addStock(request));
    }

    @PostMapping("/remove")
    public ResponseEntity<StockResponse> removeStock(@Valid @RequestBody RemoveStockRequest request) {
        return ResponseEntity.ok(stockService.removeStock(request));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferStock(@Valid @RequestBody TransferStockRequest request) {
        stockService.transferStock(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<StockResponse> getStock(
            @RequestParam Long productId,
            @RequestParam Long warehouseId) {
        return ResponseEntity.ok(stockService.getStock(productId, warehouseId));
    }
}