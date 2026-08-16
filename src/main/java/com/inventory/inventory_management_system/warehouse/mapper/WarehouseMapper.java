package com.inventory.inventory_management_system.warehouse.mapper;

import com.inventory.inventory_management_system.warehouse.dto.request.CreateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.request.UpdateWarehouseRequest;
import com.inventory.inventory_management_system.warehouse.dto.response.WarehouseResponse;
import com.inventory.inventory_management_system.warehouse.entity.Warehouse;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WarehouseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @NonNull Warehouse toEntity(@NonNull CreateWarehouseRequest request);

    @NonNull WarehouseResponse toResponse(@NonNull Warehouse entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@NonNull UpdateWarehouseRequest request, @MappingTarget @NonNull Warehouse entity);
}