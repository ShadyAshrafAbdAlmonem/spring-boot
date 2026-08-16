package com.inventory.inventory_management_system.stock.service;

import com.inventory.inventory_management_system.stock.dto.request.AddStockRequest;
import com.inventory.inventory_management_system.stock.dto.request.RemoveStockRequest;
import com.inventory.inventory_management_system.stock.dto.request.TransferStockRequest;
import com.inventory.inventory_management_system.stock.dto.response.StockResponse;

public interface StockService {
    StockResponse addStock(AddStockRequest request);
    StockResponse removeStock(RemoveStockRequest request);
    void transferStock(TransferStockRequest request);
    StockResponse getStock(Long productId, Long warehouseId);
}