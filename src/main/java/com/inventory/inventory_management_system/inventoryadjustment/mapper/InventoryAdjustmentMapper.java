package com.inventory.inventory_management_system.inventoryadjustment.mapper;

import com.inventory.inventory_management_system.inventoryadjustment.dto.request.CreateAdjustmentRequest;
import com.inventory.inventory_management_system.inventoryadjustment.dto.response.InventoryAdjustmentResponse;
import com.inventory.inventory_management_system.inventoryadjustment.entity.InventoryAdjustment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryAdjustmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "previousQuantity", ignore = true)
    @Mapping(target = "newQuantity", ignore = true)
    @Mapping(target = "adjustedQuantity", source = "adjustmentDelta")
    @Mapping(target = "adjustedAt", ignore = true)
    InventoryAdjustment toEntity(CreateAdjustmentRequest request);

    InventoryAdjustmentResponse toResponse(InventoryAdjustment entity);
}
