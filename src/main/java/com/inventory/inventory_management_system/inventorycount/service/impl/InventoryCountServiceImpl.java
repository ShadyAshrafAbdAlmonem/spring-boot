package com.inventory.inventory_management_system.inventorycount.service.impl;

import com.inventory.inventory_management_system.inventorycount.dto.request.CreateInventoryCountRequest;
import com.inventory.inventory_management_system.inventorycount.dto.response.InventoryCountResponse;
import com.inventory.inventory_management_system.inventorycount.entity.InventoryCount;
import com.inventory.inventory_management_system.inventorycount.mapper.InventoryCountMapper;
import com.inventory.inventory_management_system.inventorycount.repository.InventoryCountRepository;
import com.inventory.inventory_management_system.inventorycount.service.InventoryCountService;
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
public class InventoryCountServiceImpl implements InventoryCountService {

    private final InventoryCountRepository countRepository;
    private final InventoryCountMapper countMapper;

    @Override
    public InventoryCountResponse createCountSession(@NonNull CreateInventoryCountRequest request) {
        if (countRepository.existsByCountReference(request.getCountReference())) {
            throw new IllegalArgumentException("Count session with reference " + request.getCountReference() + " already exists");
        }

        InventoryCount inventoryCount = countMapper.toEntity(request);
        inventoryCount.setStatus("PLANNED");

        return countMapper.toResponse(countRepository.save(inventoryCount));
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryCountResponse getCountById(@NonNull Long id) {
        InventoryCount count = countRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Count session not found with id: " + id));
        return countMapper.toResponse(count);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryCountResponse> getCountsByWarehouse(Long warehouseId, Pageable pageable) {
        return countRepository.findByWarehouseId(warehouseId, pageable)
                .map(countMapper::toResponse);
    }

    @Override
    public InventoryCountResponse startCountSession(@NonNull Long id) {
        InventoryCount count = countRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Count session not found with id: " + id));

        if (!"PLANNED".equalsIgnoreCase(count.getStatus())) {
            throw new IllegalStateException("Only PLANNED count sessions can be started");
        }

        count.setStatus("IN_PROGRESS");
        count.setStartedAt(LocalDateTime.now());
        return countMapper.toResponse(countRepository.save(count));
    }

    @Override
    public InventoryCountResponse completeCountSession(@NonNull Long id) {
        InventoryCount count = countRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Count session not found with id: " + id));

        if (!"IN_PROGRESS".equalsIgnoreCase(count.getStatus())) {
            throw new IllegalStateException("Only IN_PROGRESS count sessions can be completed");
        }

        count.setStatus("COMPLETED");
        count.setCompletedAt(LocalDateTime.now());
        return countMapper.toResponse(countRepository.save(count));
    }

    @Override
    public InventoryCountResponse cancelCountSession(@NonNull Long id) {
        InventoryCount count = countRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Count session not found with id: " + id));

        if ("COMPLETED".equalsIgnoreCase(count.getStatus())) {
            throw new IllegalStateException("Completed count sessions cannot be cancelled");
        }

        count.setStatus("CANCELLED");
        return countMapper.toResponse(countRepository.save(count));
    }
}
