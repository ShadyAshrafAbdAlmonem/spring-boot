package com.inventory.inventory_management_system.stock.service.impl;

import com.inventory.inventory_management_system.stock.dto.request.AddStockRequest;
import com.inventory.inventory_management_system.stock.dto.request.RemoveStockRequest;
import com.inventory.inventory_management_system.stock.dto.request.TransferStockRequest;
import com.inventory.inventory_management_system.stock.dto.response.StockResponse;
import com.inventory.inventory_management_system.stock.entity.Stock;
import com.inventory.inventory_management_system.stock.entity.StockMovement;
import com.inventory.inventory_management_system.stock.mapper.StockMapper;
import com.inventory.inventory_management_system.stock.repository.StockMovementRepository;
import com.inventory.inventory_management_system.stock.repository.StockRepository;
import com.inventory.inventory_management_system.stock.service.StockService;
import com.inventory.inventory_management_system.stock.validator.StockValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMovementRepository stockMovementRepository;
    private final StockMapper stockMapper;
    private final StockValidator stockValidator;

    @Override
    public StockResponse addStock(AddStockRequest request) {
        Stock stock = stockRepository.findByProductIdAndWarehouseId(request.getProductId(), request.getWarehouseId())
                .orElse(Stock.builder()
                        .productId(request.getProductId())
                        .warehouseId(request.getWarehouseId())
                        .quantity(0)
                        .build());

        stock.setQuantity(stock.getQuantity() + request.getQuantity());
        Stock savedStock = stockRepository.save(stock);

        recordMovement(request.getProductId(), null, request.getWarehouseId(), request.getQuantity(), "ADD", request.getReason());

        return stockMapper.toResponse(savedStock);
    }

    @Override
    public StockResponse removeStock(RemoveStockRequest request) {
        stockValidator.validateRemoveStock(request);

        Stock stock = stockRepository.findByProductIdAndWarehouseId(request.getProductId(), request.getWarehouseId()).get();
        stock.setQuantity(stock.getQuantity() - request.getQuantity());

        Stock savedStock = stockRepository.save(stock);
        recordMovement(request.getProductId(), request.getWarehouseId(), null, request.getQuantity(), "REMOVE", request.getReason());

        return stockMapper.toResponse(savedStock);
    }

    @Override
    public void transferStock(TransferStockRequest request) {
        stockValidator.validateTransferStock(request);

        // Deduct from source
        Stock sourceStock = stockRepository.findByProductIdAndWarehouseId(request.getProductId(), request.getSourceWarehouseId()).get();
        sourceStock.setQuantity(sourceStock.getQuantity() - request.getQuantity());
        stockRepository.save(sourceStock);

        // Add to target
        Stock targetStock = stockRepository.findByProductIdAndWarehouseId(request.getProductId(), request.getTargetWarehouseId())
                .orElse(Stock.builder()
                        .productId(request.getProductId())
                        .warehouseId(request.getTargetWarehouseId())
                        .quantity(0)
                        .build());

        targetStock.setQuantity(targetStock.getQuantity() + request.getQuantity());
        stockRepository.save(targetStock);

        recordMovement(request.getProductId(), request.getSourceWarehouseId(), request.getTargetWarehouseId(), request.getQuantity(), "TRANSFER", request.getReason());
    }

    @Override
    @Transactional(readOnly = true)
    public StockResponse getStock(Long productId, Long warehouseId) {
        Stock stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        return stockMapper.toResponse(stock);
    }

    private void recordMovement(Long productId, Long sourceId, Long targetId, Integer quantity, String type, String reason) {
        StockMovement movement = StockMovement.builder()
                .productId(productId)
                .sourceWarehouseId(sourceId)
                .targetWarehouseId(targetId)
                .quantity(quantity)
                .movementType(type)
                .reason(reason)
                .timestamp(LocalDateTime.now())
                .build();

        stockMovementRepository.save(movement);
    }
}