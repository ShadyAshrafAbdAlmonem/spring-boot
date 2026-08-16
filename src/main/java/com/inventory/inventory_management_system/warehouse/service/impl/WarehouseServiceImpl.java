package com.inventory.inventory_management_system.warehouse.service.impl;

import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.warehouse.dto.request.CreateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.request.UpdateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.response.WarehouseResponse;
import com.inventory.inventory_management_system.warehouse.entity.Warehouse;
import com.inventory.inventory_management_system.warehouse.mapper.WarehouseMapper;
import com.inventory.inventory_management_system.warehouse.repository.WarehouseRepository;
import com.inventory.inventory_management_system.warehouse.service.WarehouseService;
import com.inventory.inventory_management_system.warehouse.specification.WarehouseSpecification;
import com.inventory.inventory_management_system.warehouse.validator.WarehouseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final WarehouseValidator warehouseValidator;

    @Override
    @Transactional
    public WarehouseResponse createWarehouse(@NonNull CreateWarehouseRequest request) {
        warehouseValidator.validateCreate(request);
        Warehouse warehouse = warehouseMapper.toEntity(request);
        return warehouseMapper.toResponse(Objects.requireNonNull(warehouseRepository.save(warehouse), "Saved warehouse must not be null"));
    }

    @Override
    @Transactional
    public WarehouseResponse updateWarehouse(@NonNull Long id, @NonNull UpdateWarehouseRequest request) {
        Warehouse warehouse = findEntityById(id);
        warehouseMapper.updateEntityFromDto(request, warehouse);
        return warehouseMapper.toResponse(warehouseRepository.save(warehouse));
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseResponse getWarehouseById(@NonNull Long id) {
        return warehouseMapper.toResponse(findEntityById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WarehouseResponse> getAllWarehouses(String search, Boolean active, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Warehouse> warehousePage = warehouseRepository.findAll(WarehouseSpecification.filterWarehouses(search, active), pageable);

        List<WarehouseResponse> content = warehousePage.getContent().stream()
                .map(warehouseMapper::toResponse)
                .toList();

        return PageResponse.<WarehouseResponse>builder()
                .content(content)
                .pageNumber(warehousePage.getNumber())
                .pageSize(warehousePage.getSize())
                .totalElements(warehousePage.getTotalElements())
                .totalPages(warehousePage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public void deleteWarehouse(@NonNull Long id) {
        Warehouse warehouse = findEntityById(id);
        warehouseRepository.delete(warehouse);
    }

    private @NonNull Warehouse findEntityById(@NonNull Long id) {
        return Objects.requireNonNull(warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id)));
    }
}
