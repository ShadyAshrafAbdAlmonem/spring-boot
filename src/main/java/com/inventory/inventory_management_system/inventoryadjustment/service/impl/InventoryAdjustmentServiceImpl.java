package com.inventory.inventory_management_system.inventoryadjustment.service.impl;

import com.inventory.inventory_management_system.inventoryadjustment.dto.request.CreateAdjustmentRequest;
import com.inventory.inventory_management_system.inventoryadjustment.dto.response.InventoryAdjustmentResponse;
import com.inventory.inventory_management_system.inventoryadjustment.entity.InventoryAdjustment;
import com.inventory.inventory_management_system.inventoryadjustment.mapper.InventoryAdjustmentMapper;
import com.inventory.inventory_management_system.inventoryadjustment.repository.InventoryAdjustmentRepository;
import com.inventory.inventory_management_system.inventoryadjustment.service.InventoryAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryAdjustmentServiceImpl implements InventoryAdjustmentService {

    private final InventoryAdjustmentRepository adjustmentRepository;
    private final InventoryAdjustmentMapper adjustmentMapper;

    @Override
    public InventoryAdjustmentResponse createAdjustment(@NonNull CreateAdjustmentRequest request) {
        InventoryAdjustment adjustment = adjustmentMapper.toEntity(request);
        
        // Mock current quantity check (typically retrieved via stock service/repo)
        int currentQuantity = 100;
        int newQuantity = currentQuantity + request.getAdjustmentDelta();

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Adjustment leads to negative stock balance");
        }

        adjustment.setPreviousQuantity(currentQuantity);
        adjustment.setNewQuantity(newQuantity);
        adjustment.setAdjustedAt(LocalDateTime.now());

        return adjustmentMapper.toResponse(adjustmentRepository.save(adjustment));
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryAdjustmentResponse getAdjustmentById(@NonNull Long id) {
        InventoryAdjustment adjustment = adjustmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Adjustment record not found with id: " + id));
        return adjustmentMapper.toResponse(adjustment);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryAdjustmentResponse> getAdjustmentsByWarehouse(Long warehouseId, Pageable pageable) {
        return adjustmentRepository.findByWarehouseId(warehouseId, pageable)
                .map(adjustmentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryAdjustmentResponse> getAdjustmentsByProduct(Long productId, Pageable pageable) {
        return adjustmentRepository.findByProductId(productId, pageable)
                .map(adjustmentMapper::toResponse);
    }
}
