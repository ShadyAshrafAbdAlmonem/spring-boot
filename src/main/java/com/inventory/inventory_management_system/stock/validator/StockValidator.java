package com.inventory.inventory_management_system.stock.validator;

import com.inventory.inventory_management_system.stock.dto.request.RemoveStockRequest;
import com.inventory.inventory_management_system.stock.dto.request.TransferStockRequest;
import com.inventory.inventory_management_system.stock.entity.Stock;
import com.inventory.inventory_management_system.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockValidator {

    private final StockRepository stockRepository;

    public void validateRemoveStock(RemoveStockRequest request) {
        Stock stock = stockRepository.findByProductIdAndWarehouseId(request.getProductId(), request.getWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Stock record not found for this product and warehouse"));

        if (stock.getQuantity() < request.getQuantity()) {
            throw new IllegalStateException("Insufficient stock quantity. Available: " + stock.getQuantity());
        }
    }

    public void validateTransferStock(TransferStockRequest request) {
        if (request.getSourceWarehouseId().equals(request.getTargetWarehouseId())) {
            throw new IllegalArgumentException("Source and target warehouses cannot be the same");
        }

        Stock sourceStock = stockRepository.findByProductIdAndWarehouseId(request.getProductId(), request.getSourceWarehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Source stock record not found"));

        if (sourceStock.getQuantity() < request.getQuantity()) {
            throw new IllegalStateException("Insufficient stock in source warehouse. Available: " + sourceStock.getQuantity());
        }
    }
}